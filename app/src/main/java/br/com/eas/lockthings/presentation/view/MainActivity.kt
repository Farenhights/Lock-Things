package br.com.eas.lockthings.presentation.view

import android.app.PendingIntent
import android.content.Intent
import android.media.MediaPlayer
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import br.com.eas.lockthings.R
import br.com.eas.lockthings.databinding.ActivityMainBinding
import br.com.eas.lockthings.presentation.viewmodel.LockViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: LockViewModel by viewModel()
    private var nfcAdapter: NfcAdapter? = null
    private lateinit var pendingIntent: PendingIntent
    private var successSound: MediaPlayer? = null
    private var errorSound: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeBinding()
        initializeNfc()
        initializeMediaPlayers()
        setupListeners()
        observeViewModel()
    }

    private fun initializeBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initializeNfc() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun initializeMediaPlayers() {
        successSound = MediaPlayer.create(this, R.raw.success_sound)
        errorSound = MediaPlayer.create(this, R.raw.error_sound)
    }

    private fun setupListeners() {
        binding.btnAssignKey.setOnClickListener {
            viewModel.assignKey(null)
        }

        binding.btnLock.setOnClickListener {
            viewModel.lock()
        }

        binding.btnUnlock.setOnClickListener {
            viewModel.unlock(viewModel.assignedNfcTag!!)
            viewModel.startUnlockCountdown()
        }
    }

    private fun observeViewModel() {

        viewModel.message.observe(this) { message ->
            binding.tvMessage.text = message
            if (message.contains("sucesso", ignoreCase = true)) {
                successSound?.start()
            } else if (message.contains("incorreta", ignoreCase = true)) {
                errorSound?.start()
            }
        }

        viewModel.countdown.observe(this) { countdown ->
            binding.tvCountdown.text = countdown.toString()
        }

        viewModel.lockState.observe(this) { isUnlocked ->
            if (isUnlocked) {
                binding.ivLock.setImageResource(R.drawable.ic_unlock)
                binding.tvMessage.text = "Cadeado aberto"
            } else {
                binding.ivLock.setImageResource(R.drawable.ic_lock)
                binding.tvMessage.text = "Cadeado fechado"
            }
        }
    }

    private fun playSoundBasedOnMessage(message: String) {
        when {
            message.contains("sucesso", ignoreCase = true) -> successSound?.start()
            message.contains("incorreta", ignoreCase = true) -> errorSound?.start()
        }
    }

    private fun updateLockImage(isUnlocked: Boolean) {
        val imageResource = if (isUnlocked) R.drawable.ic_unlock else R.drawable.ic_lock
        binding.ivLock.setImageResource(imageResource)
    }

    override fun onResume() {
        super.onResume()
        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, null, null)
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            val tag: Tag? = it.getParcelableExtra(NfcAdapter.EXTRA_TAG)
            tag?.let { nfcTag ->
                val tagId = nfcTag.id.joinToString("") { byte -> "%02x".format(byte) }
                if (viewModel.assigningKey) {
                    viewModel.assignKey(tagId)
                } else {
                    viewModel.unlock(tagId)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        successSound?.release()
        errorSound?.release()
    }
}
