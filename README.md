# Lock-Things


O LockThings é um aplicativo Android que permite ao usuário abrir e fechar um cadeado utilizando uma tag NFC pré-definida. O aplicativo utiliza a arquitetura MVVM com Clean Architecture, Kotlin, Coroutines, Data Binding, Constraint Layout, Koin para injeção de dependência e testes unitários.

## Funcionalidades

1. **Atribuir Chave ao Cadeado**
    - O usuário pode atribuir uma tag NFC ao cadeado.
    - Exibição de mensagem de sucesso ao atribuir a chave.
    - Mostrar cadeado aberto após atribuir a chave.

2. **Fechar e Abrir o Cadeado**
    - Botão para fechar o cadeado.
    - Botão para abrir o cadeado solicitando a tag NFC pré-cadastrada.
    - Exibição de mensagem de sucesso ao abrir o cadeado com a tag correta.
    - Emissão de som de erro e mensagem de chave incorreta ao tentar abrir o cadeado com a tag errada.

3. **Contador Regressivo**
    - Contador de 30 segundos para abertura do cadeado.
    - Cancelamento da ação de abrir o cadeado após o tempo esgotado.

## Tecnologias Utilizadas

- Kotlin
- MVVM com Clean Architecture
- Coroutines
- Data Binding
- Constraint Layout
- Koin
- Testes Unitários

## Estrutura do Projeto

```
LockThings/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   ├── br/
│   │   │   │   │   ├── eas/
│   │   │   │   │   │   ├── lockthings/
│   │   │   │   │   │   │   ├── presentation/
│   │   │   │   │   │   │   │   ├── view/
│   │   │   │   │   │   │   │   │   └── MainActivity.kt
│   │   │   │   │   │   │   │   ├── viewmodel/
│   │   │   │   │   │   │   │       └── LockViewModel.kt
│   │   │   │   │   │   │   └── LockThingsApp.kt
│   │   │   │   ├── resources/
│   │   │   │   │   ├── drawable/
│   │   │   │   │   │   ├── ic_lock.xml
│   │   │   │   │   │   └── ic_unlock.xml
│   │   │   │   │   ├── layout/
│   │   │   │   │   │   └── activity_main.xml
│   │   │   │   │   ├── raw/
│   │   │   │   │   │   ├── success_sound.mp3
│   │   │   │   │   │   └── error_sound.mp3
│   │   │   │   │   ├── values/
│   │   │   │   │       └── strings.xml
│   │   └── AndroidManifest.xml
└── README.md
```

## Configuração do Projeto

### Pré-requisitos

- Android Studio 4.0 ou superior
- JDK 1.8 ou superior

### Instalação

1. Clone o repositório:
    ```bash
    git clone https://github.com/seu-usuario/LockThings.git
    cd LockThings
    ```

2. Abra o projeto no Android Studio.

3. Sincronize o projeto com os arquivos Gradle.

## Uso

### Atribuir Chave ao Cadeado

1. Abra o aplicativo.
2. Na tela inicial, clique em "Atribuir chave ao cadeado".
3. Aproxime a tag NFC do dispositivo.
4. Uma mensagem de sucesso será exibida e o cadeado será mostrado como aberto.

### Fechar o Cadeado

1. Clique no botão "Fechar Cadeado".
2. O cadeado será fechado e uma mensagem será exibida.

### Abrir o Cadeado

1. Clique no botão "Abrir Cadeado".
2. Aproxime a tag NFC pré-cadastrada.
3. Se a tag estiver correta, o cadeado será aberto e uma mensagem de sucesso será exibida.
4. Se a tag estiver incorreta, um som de erro será emitido e uma mensagem de chave incorreta será exibida.

### Contador Regressivo

- Ao iniciar a ação de abertura do cadeado, um contador regressivo de 30 segundos será iniciado.
- Se o tempo esgotar, a ação de abrir o cadeado será cancelada.
