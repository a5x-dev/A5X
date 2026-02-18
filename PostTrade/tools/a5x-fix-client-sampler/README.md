### A5X Post‑Trading FIX Client (Sample)

#### Visão geral
Este projeto é um cliente FIX 4.4 de exemplo usando QuickFIX/J, voltado ao envio de mensagens de pós‑negociação (por exemplo, `ApplicationMessage`, `MsgType=U8`) para a A5X. Ele ajuda você a:
- Iniciar uma sessão FIX como Initiator
- Efetuar Logon (enviando `Username` e `Password`)
- Enviar uma mensagem contendo um payload JSON

O conteúdo JSON a ser enviado é definido por esquema em cada mensagem no repositório oficial A5X. Consulte os esquemas e exemplos aqui: https://github.com/a5x-dev/A5X

#### Requisitos
- Java 21+
- Maven 3.9+
- Acesso a um FIX Acceptor (host/porta, IDs de sessão e credenciais válidas)

#### Estrutura do projeto
```
/a5x-post-trading-fix-client-sample
├─ pom.xml
├─ src/main/java/br/com/a5x/Main.java          # Bootstrap do cliente
├─ src/main/java/br/com/a5x/FixClient.java     # Lógica FIX (QuickFIX/J Application)
└─ src/main/resources/client.cfg               # Configurações QuickFIX/J

```

#### Como compilar
```
# No diretório do projeto
mvn -q -DskipTests package
```
Isso irá compilar o projeto e copiar os recursos para `target/classes`.

#### Configuração (client.cfg)
Edite `src/main/resources/client.cfg` com os dados da sua sessão e dicionário.

Campos importantes:
- `SocketConnectHost` / `SocketConnectPort`: endereço do seu Acceptor FIX
- `SenderCompID` / `TargetCompID`: IDs da sessão
- `Username` / `Password`: credenciais (podem ser omitidas se você usar variáveis de ambiente, veja abaixo)
- `UseDataDictionary=N`: desabilita validação de mensagens pelo dicionário ainda não liberado


#### Credenciais via variáveis de ambiente (opcional)
O código tenta ler `Username`/`Password` do `client.cfg`. Se não encontrar, tenta variáveis de ambiente:
- `FIX_USERNAME`
- `FIX_PASSWORD`

No Windows PowerShell:
```
$env:FIX_USERNAME = "seu.usuario@empresa.com"
$env:FIX_PASSWORD = "SuaSenhaForte"
```

#### Executando o cliente
1) Garanta que o `client.cfg` esteja correto e que o dicionário seja acessível
2) Compile o projeto (`mvn package`)
3) Execute a aplicação:
```
java -cp target\a5x-post-fix-0.0.1-SNAPSHOT.jar;target\dependency\* br.com.a5x.Main
```
Observação: se você não usa o plugin `maven-dependency-plugin` para copiar dependências, uma alternativa simples é:
```
mvn -q exec:java -Dexec.mainClass="br.com.a5x.Main"
```

Quando a sessão conectar e fizer Logon, o menu aparecerá:
```
1 - Send New Message
2 - Exit
Choose:
```

#### Enviando uma mensagem
Quando você escolher “1 - Send New Message”, o cliente pedirá:
- Caminho do arquivo JSON com o conteúdo do payload
- Nome da mensagem, sendo exatamente o nome da mensagem do portaldev e/ou do schema (sem o .json)

Passos típicos:
1) Obtenha o esquema e exemplos do payload no repositório A5X: https://github.com/a5x-dev/A5X
2) Gere ou edite seu arquivo JSON conforme o esquema da mensagem de destino
3) Informe o caminho do JSON ao cliente
4) Informe o “message name” quando solicitado

O cliente irá:
- Ler o conteúdo JSON do arquivo fornecido
- Preencher os campos customizados (por exemplo, tamanho e conteúdo) conforme implementado
- Enviar a mensagem pela sessão FIX ativa

Importante: O dicionário de [pós-negociação da A5X](https://portaldev.a5x.com.br/pt-br/P%C3%B3sNegocia%C3%A7%C3%A3o) define campos obrigatórios e grupos para cada `MsgType`. Se o Acceptors estiver validando contra o dicionário, sua mensagem precisa atender aos requisitos (campos obrigatórios no corpo, grupos, tipos corretos). Ajuste o JSON e/ou o mapeamento da mensagem conforme o dicionário do seu ambiente.

#### Solução de problemas
- Não conecta ao servidor
    - Revise `SocketConnectHost/Port`, firewall e disponibilidade do Acceptor
- `Logon` rejeitado
    - Confira `Username`/`Password`, validade dos IDs de sessão e `BeginString`
- Rejeição de mensagem (BusinessReject/SessionReject)
    - Consulte o `event.log` para identificar campos obrigatórios ausentes, tipos incorretos ou grupos malformados


#### Onde encontrar o conteúdo JSON
Os conteúdos e schemas das mensagens A5X estão no repositório oficial:
- Repositório: https://github.com/a5x-dev/A5X
- Cada mensagem possui um schema com a definição do JSON esperado

Use esses schemas como fonte de verdade para montar os JSONs que você fornecerá ao client no momento do envio. Os exemplos dentro dos schemas das mensagens podem ser salvos em arquivos locais para ser usados nesse envio.


#### Observações finais
- Este projeto é um exemplo de referência.
- Trate segredos com cuidado: prefira variáveis de ambiente ou vaults ao versionar `client.cfg`.

