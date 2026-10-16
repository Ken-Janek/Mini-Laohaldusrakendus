# Mini laohaldusrakendus

Lihtne Java konsoolirakendus. Väliseid teeke ega andmebaasi pole vaja.
Andmed on mälus ja kaovad programmi sulgemisel. Alguses on laos Piim, Leib ja Hiir.

## Käivitamine

Vaja on JDK 17 või uuemat (nii `java` kui ka `javac`). Ava terminal projektikaustas:

```powershell
mkdir out -Force
javac -encoding UTF-8 -d out src/*.java
java -Dfile.encoding=UTF-8 -cp out Main
```

Kui Java pole PATH-is, saab selles töökaustas kasutada kontrollimiseks alla laaditud JDK-d:

```powershell
$jdkBin = (Get-ChildItem .tools/jdk -Directory | Select-Object -First 1).FullName + '\bin'
& "$jdkBin/javac.exe" -encoding UTF-8 -d out src/*.java
& "$jdkBin/java.exe" -Dfile.encoding=UTF-8 -cp out Main
```

`.tools` ja `out` ei kuulu Git-reposse. Teises arvutis paigalda JDK.

## Kasutamine

1. Kõik tooted: kuvab nime, hinna, koguse ja liigile vastava lisainfo.
2. Lisa toode: vali toit või elektroonika, sisesta nimi, hind ja kogus.
3. Muuda kogust: sisesta toote nimi ja muutus, näiteks `3` või `-2`.
4. Otsi nime järgi: otsib täpset nime, suur- ja väiketähed ei loe.
5. Lao koguväärtus: liidab kõikide toodete hinna ja koguse korrutised.
0. Välju.

Hinnas sobib punkt või koma. Negatiivne hind või kogus, tühi nimi ja korduv
tootenimi on keelatud. Sobimatu sisendi järel saab menüüs jätkata.

## Hindamiskriteeriumid ja nende asukohad

| Osa | Kus ja kuidas see lahenduses esineb? |
| --- | --- |
| OOP-põhimõtted | **Pärilus:** [FoodProduct.java](src/FoodProduct.java) ja [ElectronicProduct.java](src/ElectronicProduct.java) kasutavad `extends Product`, mis tähendab, et need on `Product`-klassi alamklassid. Need saavad kasutada ühiseid meetodeid, näiteks `getName()` ja `changeQuantity()`, ning lisavad oma `getExtraInfo()` teostuse. Nii ei pea toodete ühist loogikat mõlemas klassis uuesti kirjutama.<br><br>**Kapseldamine:** [Product.java](src/Product.java) väljad `name`, `price` ja `quantity` on `private`, seega teised klassid ei saa neid otse lugeda ega muuta. Väärtusi loetakse getteritega, näiteks `getQuantity()`, ning kogust muudetakse `changeQuantity()` kaudu. See meetod kontrollib, et uus kogus ei oleks negatiivne ega ületaks `int`-tüübi suurimat väärtust. Nii ei saa muu kood kontrollist mööda minna ja tootele vigast kogust määrata.<br><br>**Polümorfism:** [Main.java](src/Main.java) kuvab erinevaid tooteid ühise `Product`-tüübi kaudu. Kuvamisel kutsub `Product.toString()` meetodit `getExtraInfo()`. Java valib tegeliku objekti järgi, kas käivitada `FoodProduct` või `ElectronicProduct` teostus: toidul kuvatakse säilivusaja meeldetuletus ja elektroonikal kuivas hoidmise soovitus. Nii saab sama kuvamiskood töötada eri tooteliikidega ilma iga liigi jaoks eraldi tingimust kirjutamata.<br><br>**Abstraktsioon:** [Product.java](src/Product.java) on `abstract` klass, mis kirjeldab toodete ühiseid andmeid ja toiminguid. Sellest ei saa otse objekti luua käsuga `new Product(...)`; luua tuleb konkreetne toidutoode või elektroonikatoode. Meetod `getExtraInfo()` on samuti `abstract`: see ütleb, et igal tooteliigil peab olema lisainfo, kuid täpse sisu määrab alamklass. Nii saab ladu kasutada toodete ühiseid toiminguid, teadmata iga tooteliigi eripära. |
| Andmetüübid ja andmestruktuurid | **String:** [Product.java](src/Product.java) väli `name` hoiab toote nime tekstina, näiteks „Piim”. Tekstitüüp sobib, sest nimi võib sisaldada tähti ja tühikuid.<br><br>**int:** sama faili `quantity` hoiab kogust täisarvuna, näiteks 10. Selles rakenduses loetakse terveid tooteühikuid, seega murdosa pole vaja.<br><br>**double:** `price` hoiab komakohaga hinda, näiteks 1.50. `getValue()` korrutab hinna kogusega ja annab selle toote laoväärtuse. See on õppeülesandes lihtne valik; täpseks rahaarvestuseks sobiks paremini `BigDecimal` või täisarvulised sendid, sest `double` võib anda ümardusvigu.<br><br>**boolean:** [Main.java](src/Main.java) muutuja `running` on kas `true` või `false`. `while (running)` kordab menüüd ning väljumisvalik määrab väärtuseks `false`. Nii saab programmi kordamist ühe muutujaga juhtida.<br><br>**ArrayList:** [Inventory.java](src/Inventory.java) loend `ArrayList<Product>` hoiab kõiki tooteid. `addProduct()` lisab toote loendi lõppu ja `for`-tsükkel võimaldab kõik tooted läbi käia. Loend kasvab vajaduse järgi, seega ei pea toodete arvu ette teadma.<br><br>**Otsing:** `findByName()` võrdleb järjest iga toote nime `equalsIgnoreCase()` abil, nii et „Piim” ja „piim” annavad sama vaste. Meetod tagastab leitud toote või `null`, kui vastet pole. Otsing on O(n): halvimal juhul vaadatakse läbi kogu loend. Väikese lao jaoks on see piisav ja lihtne mõista. |
| Praktiline programmeerimine | **Menüü:** [Main.java](src/Main.java) meetod `main()` loob kolm algtoodet ning `switch` valib sisestatud numbri järgi tegevuse. See annab kasutajale ühe koha kõigi laotoimingute tegemiseks.<br><br>**Kuvamine ja lisamine:** valik 1 käib toodete loendi läbi ja kuvab iga toote. Valik 2 kutsub `addProduct()` meetodit, mis küsib liigi, nime, hinna ja koguse ning loob sobiva tooteobjekti. `Inventory.addProduct()` kontrollib, et sama nimega toodet poleks juba olemas.<br><br>**Koguse muutmine ja otsing:** valik 3 leiab toote nime järgi ning kutsub `changeQuantity()` meetodit. Näiteks muutus `-2` vähendab kogust kahe võrra. Valik 4 kasutab sama nimeotsingut toote kuvamiseks. Ühine otsingumeetod väldib sama loogika kordamist.<br><br>**Koguväärtus:** valik 5 kutsub [Inventory.java](src/Inventory.java) meetodit `getTotalValue()`, mis liidab iga toote hinna ja koguse korrutise. Näiteks 10 ühikut hinnaga 1.50 annavad väärtuseks 15.00 eurot.<br><br>**Vigade käsitlemine:** `readInt()` ja hinna lugemine kontrollivad arvulist sisendit; `Product` kontrollib hinda ja kogust. `try/catch` kuvab vea korral teate ja lubab menüüs jätkata. Tegeliku näidiskäivituse väljund [kaivitus.txt](evidence/kaivitus.txt) näitab lisamist, otsingut, koguväärtust ning keelatud kogusemuutust. |
| Iseseisev oskus | **Käivitamine:** jaotise „Käivitamine” käsk `javac` kompileerib `.java` failid `.class` failideks ning `java` käivitab `Main`-klassi. Neid ise kasutades oskad projekti käivitada ja märgata võimalikke kompileerimisvigu.<br><br>**Selgitamine:** ava näiteks `Product.changeQuantity()` ja selgita, kuidas uus kogus arvutatakse, miks seda kontrollitakse ning millal tekib veateade. Ennusta enne katsetamist, mis juhtub, kui laos on 4 toodet ja muutus on `-5`. Seejärel võrdle ennustust programmi tulemusega.<br><br>**Muutmine:** jaotis „Harjutus esitluseks” pakub võimalust lisada otsa saanud toodete kuvamine. Selleks tuleb mõista toodete läbimise tsüklit ja tingimust `getQuantity() == 0`. Proovi muudatust ise ning kontrolli tulemust nullkogusega tootega.<br><br>**Tõendamine:** valmis kood ja juhend toetavad õppimist. Iseseisvat oskust näitad esitlusel ise programmi kasutades, valitud ridu selgitades ja õpetaja antud muudatust tehes. |
| Töökorraldus | **Klasside ülesanded:** kaustas [src](src) kirjeldab `Product` ühiseid tooteandmeid, `FoodProduct` ja `ElectronicProduct` tooteliike, `Inventory` lao toiminguid ning `Main` kasutajaga suhtlemist. Nii on lihtsam leida, millises failis vajalikku muudatust teha.<br><br>**Meetodid ja nimed:** näiteks `findByName()`, `changeQuantity()` ja `getTotalValue()` ütlevad nimega, mida nad teevad. Eraldi meetodeid saab kasutada mitmest kohast, mis vähendab korduvat koodi.<br><br>**Kommentaarid:** iga Java faili algus selgitab faili eesmärki ning koodi kommentaarid aitavad mõista näiteks pärilust, sisendikontrolli ja tsükleid. See toetab koodi lugemist ja esitlusel selgitamist.<br><br>**Tööpäevik:** allpool olev päevik kirjeldab lahenduse valmimise järjekorda ja keerulisemat kohta. Selle põhjal saab arenduse käiku meenutada ning lisada oma õppimise tähelepanekud.<br><br>**Git:** jaotises „Commit'id” on arendusetappide loetelu. Käsk `git log --oneline` näitab commit'ide ajalugu ning `git show <räsi>` ühe commit'i muudatusi. Näiteks eraldi tooteklasside, lao ja menüü commit'id võimaldavad näha, kuidas lahendus sammhaaval kasvas. |

## Tööpäevik (AI abil tehtud töö kirjeldus)

Kõigepealt valmis abstraktne tooteklass koos kahe alamklassiga.
Seejärel lisandus ArrayListi kasutav ladu, nimeotsing ja koguväärtuse arvutus.
Kolmandaks valmis konsoolimenüü ning sisendite ja koguse kontroll.
Kõige rohkem tähelepanu nõudis vigase sisendi käsitlemine nii, et programm saaks jätkata.
Lõpuks kompileeriti lahendus ning salvestati näidiskäivituse väljund.

See kirjeldab AI abil tehtud tööd, mitte sinu iseseisva õppimise tõendit.
Käivita ja proovi kood ise läbi ning täienda päevikut oma tähelepanekutega.

## Käivitustõend

`evidence/kaivitus.txt` on päris Java protsessi väljund ja `evidence/sisend.txt`
sellele antud sisend. `evidence/kaivitus.cast` on sama väljundi tekstipõhine
terminalisalvestis asciinema v2 vormingus (esitusajad on lisatud tagantjärele).
Seda saab vaadata asciinema mängijaga: `asciinema play evidence/kaivitus.cast`.
See pole ekraanivideo ega iseseisvalt kasutamise tõend: tee esitamiseks ka enda
ekraanipilt või salvestus, kui õpetaja eeldab just seda.

Näidiskäivituses lisatakse Juust hinnaga 3,50 ja kogusega 4, otsitakse `juust`
ning saadakse koguväärtuseks 69,00 EUR. Koguse vähendamine viie võrra keelatakse;
ühe võrra vähendamise järel on koguväärtus 65,50 EUR.

## Harjutus esitluseks

Proovi iseseisvalt lisada menüüsse valik 6, mis kuvab ainult otsa saanud tooted.
Leia valiku 1 tsükkel ja mõtle, kuhu lisada tingimus `product.getQuantity() == 0`.
Katseta, vähendades mõne toote koguse nullini.

Enne esitlust oska selgitada `super(...)`, `@Override`, `private`, `final`,
`null`, `try/catch` ja seda, miks koguväärtuses kasutatakse korrutamist.
Ennusta ka, mis juhtub, kui lisad sama nime teist korda või sisestad koguseks teksti.

## Commit'id

Esimesed kaks sisulist commit'i:

- `e7797ad` – Lisa kapseldatud abstraktne toode ja kaks alamklassi.
- `9477fb0` – Lisa ArrayListiga ladu, nimeotsing ja koguväärtus.

Kolmas sisuline commit: `Lisa konsoolimenüü, sisendikontroll ja käivitustõend`.
Kõik täpsed räsi väärtused leiab käsuga `git log --oneline`.

Need on arendusetappide commit'id selles tööseansis, mitte väide varasemast iseseisvast tööst.
