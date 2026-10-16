# Mini laohaldusrakendus

Java konsoolirakendus väikese poe laoseisu haldamiseks.
Alguses on laos Piim, Leib ja Hiir. Andmed säilivad kuni programmi sulgemiseni.

## Käivitamine

Vaja on JDK 17 või uuemat. Ava PowerShell projektikaustas:

```powershell
mkdir out -Force
javac -encoding UTF-8 -d out src/*.java
java -Dfile.encoding=UTF-8 -cp out Main
```

`javac` kompileerib koodi ja `java` käivitab programmi. Väliseid teeke pole vaja.

## Menüü

| Valik | Tegevus |
| --- | --- |
| 1 | Kuva kõik tooted |
| 2 | Lisa toit või elektroonikatoode |
| 3 | Muuda kogust, näiteks `3` või `-2` |
| 4 | Otsi täpse nime järgi; suured ja väikesed tähed ei loe |
| 5 | Kuva lao koguväärtus: hind × kogus, kõik tooted kokku |
| 0 | Välju |

Hinnas sobib punkt või koma. Negatiivne hind või kogus, tühi nimi ja korduv
nimi on keelatud. Vigase sisendi korral kuvatakse teade ja saab jätkata.

## Koodi ülesehitus

- [Product.java](src/Product.java) – ühised tooteandmed ja koguse kontroll.
- [FoodProduct.java](src/FoodProduct.java) – toidutoote lisainfo.
- [ElectronicProduct.java](src/ElectronicProduct.java) – elektroonikatoote lisainfo.
- [Inventory.java](src/Inventory.java) – toodete loend, lisamine, otsing ja koguväärtus.
- [Main.java](src/Main.java) – menüü ja kasutaja sisend.

### OOP-põhimõtted

- **Pärilus:** `extends Product` annab tooteliikidele ühised meetodid, näiteks
  `getName()`. Nii ei pea sama loogikat mõlemas alamklassis kordama.
- **Kapseldamine:** `Product` väljad on `private`. Getterid loevad väärtusi;
  `changeQuantity()` muudab kogust ja keelab negatiivse tulemuse.
- **Polümorfism:** `Product.toString()` kutsub `getExtraInfo()` meetodit.
  Toidul ja elektroonikal kuvatakse erinev lisainfo sama kutse kaudu.
- **Abstraktsioon:** `abstract Product` kirjeldab toodete ühist alust.
  Sellest otse objekti luua ei saa; alamklass peab teostama `getExtraInfo()`.
  Nii saab ladu kasutada ühiseid toiminguid tooteliigi eripära teadmata.

### Andmetüübid ja loend

| Tüüp | Kasutus ja põhjus |
| --- | --- |
| `String` | Toote nimi on tekst |
| `int` | Kogus loeb terveid ühikuid |
| `double` | Hind ja koguväärtus vajavad komakohti |
| `boolean` | `running` määrab, kas menüü jätkab tööd |

`Inventory` kasutab `ArrayList<Product>`-i, sest see kasvab vajaduse järgi ja
seda on lihtne tsükliga läbida. `findByName()` võrdleb nimesid järjest
`equalsIgnoreCase()` abil ning tagastab toote või `null`. Otsing on O(n),
mis sobib väikesele laole. Täpseks rahaarvestuseks sobiks `double` asemel
`BigDecimal` või täisarvulised sendid.

## Tööpäevik

AI abil valmis kõigepealt `Product` koos kahe alamklassiga.
Seejärel lisandusid lao loend, otsing ja koguväärtuse arvutus.
Kolmandaks valmis menüü; keerulisem koht oli vigaste sisendite käsitlemine.
Lõpuks kompileeriti programm ja salvestati näidiskäivitus.
Täienda päevikut oma õppimise tähelepanekutega.

## Käivitustõend

- [Väljund](evidence/kaivitus.txt) ja [sisend](evidence/sisend.txt) pärinevad tegelikust käivitusest.
- [Terminalisalvestis](evidence/kaivitus.cast) sisaldab sama väljundit; esitusajad on lisatud tagantjärele.

Näites lisatakse ja otsitakse Juustu: koguväärtus on **69,00 €**.
Lubatud koguse vähendamise järel on see **65,50 €**; liiga suur vähendamine keelatakse.

## Esitluseks

Käivita programm ise ja oska selgitada valitud koodiridu.
Harjuta valiku lisamist, mis kuvab ainult tooted, mille `getQuantity() == 0`.
Valmis kood ja salvestis üksi ei tõenda iseseisvat oskust.

## Git-ajalugu

Kolm esimest sisulist arendusetappi:

| Commit | Muudatus |
| --- | --- |
| `e7797ad` | Abstraktne toode ja alamklassid |
| `9477fb0` | Ladu, otsing ja koguväärtus |
| `8af2be8` | Menüü, sisendikontroll ja käivitustõend |

Kogu ajalugu: `git log --oneline`. Ühe commit'i muudatused: `git show <räsi>`.
