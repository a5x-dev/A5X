
# A5X Schemas & Exemplos


> **Bem-vindo!** Este repositório centraliza **schemas** (definições de mensagens/formatos) e **exemplos** para integrações com os serviços A5X. É o ponto de partida para quem precisa **validar**, **simular** e **implementar** trocas de mensagens (por exemplo, *PostTrade/EX5* e *Trade*).



## Visão Geral
O objetivo é oferecer artefatos confiáveis para **desenvolvedores**, **analistas** e **testadores** que trabalham com os domínios do A5X. 

- **Schemas**: definem a estrutura dos objetos/mensagens (ex.: JSON e XML). 
- **Exemplos**: instâncias reais ou fictícias que demonstram o uso correto dos schemas em cenários comuns.

> Dica: a página inicial do **Portal do Desenvolvedor A5X** traz a visão macro, links de documentação e novidades. Este repositório foca na **parte técnica** (artefatos e exemplos).



## Status
> **Prévia Pública (Early Stage)**: schemas e exemplos estão em evolução contínua. Quebra de compatibilidade pode ocorrer entre versões.

- **Estabilidade**: crescente, com revisões frequentes.
- **Feedbacks**: são bem-vindos via *Issues* ou contato com o atendimento da A5X.


## Tips!
> Clientes com stack **java**, podem fazer utilização do utilitário [javaschema2pojo](https://www.jsonschema2pojo.org/), também disponível através do repositório github [github.com/joelittlejohn/jsonschema2pojo](https://github.com/joelittlejohn/jsonschema2pojo). Na seção de releases, é possível obter o build em diferentes compactações, e uma vez extraído no ambiente local, via terminal, no diretório da A5X, por exemplo, para gerar os stubs de todos os artefatos relacionados a captura e alocação, pode-se disparar o comando;

```
$ mkdir -p ~/a5x-stubs-generated/

$ /dir-extracted-jsonschema2pojo/bin/jsonschema2pojo \
  --source ~/git/A5X/PostTrade/EX5/schemas/v1/messages/capture_and_allocation/*.json \
  --target ~/a5x-stubs-generated/

$ ls ~/a5x-stubs-generated/
AccountIdentification.java              AllocationStatus.java       Ex5AlcCan0201.java  Ex5TrdAgg0101.java             Instrument.java          StrategyTrade.java
AllocationDistributionCancelation.java  AveragePriceIndicator.java  Ex5AlcInf0301.java  Ex5TrdCan0201.java             NoCompressionGroup.java  TradeDetails.java
AllocationDistributionInstruction.java  BreakingRules.java          Ex5AlcIns0401.java  Ex5TrdNtf0301.java             Pagination.java          Trade.java
AllocationIdentification.java           CompressionGroup.java       Ex5AlcReq0501.java  GiveUp.java                    PartyId.java             Trader.java
AllocationResponse.java                 Ex5AlcApr0101.java          Ex5AlcSta0601.java  InstrumentIdentification.java  StatusError.java
```



## Suporte
- **Portal do Desenvolvedor A5X**: documentação, guias e novidades.

> Em caso de dados sensíveis, utilize os canais privados acordados com a A5X.

---

<p align="center">© A5X – Repositório de Schemas & Exemplos</p>
