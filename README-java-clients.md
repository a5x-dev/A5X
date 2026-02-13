
# A5X Schemas & Exemplos

## Tips!

Clientes com stack **java**, podem fazer utilização do utilitário [javaschema2pojo](https://www.jsonschema2pojo.org/), também disponível através do repositório github [github.com/joelittlejohn/jsonschema2pojo](https://github.com/joelittlejohn/jsonschema2pojo). Na seção de releases, é possível obter o build em diferentes compactações, e uma vez extraído no ambiente local, via terminal, no diretório da A5X, por exemplo, para gerar os stubs de todos os artefatos relacionados a captura e alocação, pode-se disparar o comando;

``` 
$ mkdir -p ~/a5x-stubs-generated/

$ /dir-extracted-jsonschema2pojo/bin/jsonschema2pojo \
  --source ~/git/A5X/PostTrade/EX5/schemas/v1/messages/capture_and_allocation/*.json \
  --target ~/a5x-stubs-generated/

$ ls ~/a5x-stubs-generated/
.
AccountIdentification.java              AllocationStatus.java       Ex5AlcCan0201.java  Ex5TrdAgg0101.java             Instrument.java          StrategyTrade.java
AllocationDistributionCancelation.java  AveragePriceIndicator.java  Ex5AlcInf0301.java  Ex5TrdCan0201.java             NoCompressionGroup.java  TradeDetails.java
AllocationDistributionInstruction.java  BreakingRules.java          Ex5AlcIns0401.java  Ex5TrdNtf0301.java             Pagination.java          Trade.java
AllocationIdentification.java           CompressionGroup.java       Ex5AlcReq0501.java  GiveUp.java                    PartyId.java             Trader.java
AllocationResponse.java                 Ex5AlcApr0101.java          Ex5AlcSta0601.java  InstrumentIdentification.java  StatusError.java
```


No exemplo acima, os stubs são gerados **sem a declaração do package**.
Para deixar já com o pacote desejado, é possível;
``` 
$ mkdir -p ~/a5x-stubs-generated/

$ /dir-extracted-jsonschema2pojo/bin/jsonschema2pojo \
  --source ~/git/A5X/PostTrade/EX5/schemas/v1/messages/capture_and_allocation/*.json \
  --target ~/a5x-stubs-generated/
  --package com.meupacote.a5x

$ ls ~/a5x-stubs-generated/com/meupacote/a5x
.
AccountIdentification.java              AllocationStatus.java       Ex5AlcCan0201.java  Ex5TrdAgg0101.java             Instrument.java          StrategyTrade.java
AllocationDistributionCancelation.java  AveragePriceIndicator.java  Ex5AlcInf0301.java  Ex5TrdCan0201.java             NoCompressionGroup.java  TradeDetails.java
AllocationDistributionInstruction.java  BreakingRules.java          Ex5AlcIns0401.java  Ex5TrdNtf0301.java             Pagination.java          Trade.java
AllocationIdentification.java           CompressionGroup.java       Ex5AlcReq0501.java  GiveUp.java                    PartyId.java             Trader.java
AllocationResponse.java                 Ex5AlcApr0101.java          Ex5AlcSta0601.java  InstrumentIdentification.java  StatusError.java
```


---

<p align="center">© A5X – Repositório de Schemas & Exemplos</p>
