# Carteira Digital com Blockchain (Java)

Aplicação desktop em Java (Swing) que simula uma carteira digital de criptomoedas. Todas as transações ficam registadas numa blockchain com consenso **Proof of Stake**.

Projeto de grupo desenvolvido na unidade curricular de **Programação Orientada a Objetos** da Licenciatura em Engenharia Informática (Instituto Politécnico de Tomar), 2025/2026.

## Funcionalidades

- **Registo e login de utilizadores.** Cada utilizador tem um par de chaves (criptografia de curva elítica). A chave privada é guardada cifrada com AES-256-GCM, a partir da password do utilizador.
- **Carteira multimoeda:** BitCoin, Ether, Solana e TemplarCoin.
- **Transferências entre utilizadores.** Cada transação é assinada digitalmente pelo remetente e guardada num novo bloco da blockchain. Antes de concluir, a aplicação verifica se o saldo é suficiente.
- **Administrador.** O utilizador `SYSTEM` emite moedas e atribui-as aos restantes utilizadores.
- **Consenso Proof of Stake.** O validador de cada bloco é escolhido com base no stake. Cada bloco guarda a hash do bloco anterior e a assinatura do validador.
- **Validação da cadeia.** É possível verificar a integridade de cada bloco: a assinatura e a hash.
- **Consulta** do saldo e do histórico de movimentos por moeda.
- **Exportação** dos movimentos para CSV.

## Tecnologias

- Java 23 e Swing (interface gráfica)
- `java.security` e `javax.crypto`: assinaturas digitais, SHA-256, AES-GCM e PBKDF2
- Projeto NetBeans (Ant)

## Estrutura

```
src/
├── blockchain/core      # Bloco, blockchain e transação
├── blockchain/core/pos  # Bloco Proof of Stake, seleção de validador
├── blockchain/helpers   # Segurança/criptografia, utilitários, exportação CSV
├── blockchain/user      # Utilizador e gestão de chaves
├── moedas               # Hierarquia de moedas (classe abstrata Currency)
└── carteiradigital      # Interface gráfica (login, carteira, confirmação)
```

## Como executar

1. Abrir o projeto no **NetBeans** (ou noutro IDE com suporte a projetos Ant) com o **JDK 23** ou superior.
2. Executar a classe principal `carteiradigital.Login`.
3. Registar um utilizador no separador **Register** e iniciar sessão.

Os ficheiros de dados (`*.priv`, `*.pub`, `*.blk`) são criados localmente na primeira utilização e não estão incluídos no repositório.

## Autoria

- João Valente
- Carlos Covão

O núcleo da blockchain e as classes de segurança foram fornecidos pelo docente da unidade curricular, Prof. António Manso (UPT), como base para o trabalho.
