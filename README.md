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
| OOP-põhimõtted | **Pärilus:** [FoodProduct.java](src/FoodProduct.java) ja [ElectronicProduct.java](src/ElectronicProduct.java) kasutavad `extends Product`. **Kapseldamine:** [Product.java](src/Product.java) hoiab välju `private` ning kontrollib koguse muutmist meetodis `changeQuantity()`. **Polümorfism:** `Product.toString()` kutsub `getExtraInfo()` meetodit, mille tulemus sõltub tegelikust alamklassist. **Abstraktsioon:** `Product` on abstraktne klass ja `getExtraInfo()` abstraktne meetod, mille alamklassid peavad teostama. |
| Andmetüübid ja andmestruktuurid | [Product.java](src/Product.java): `String name` on nimi, `double price` hind ja `int quantity` täisarvuline kogus. [Main.java](src/Main.java): `boolean running` määrab, kas menüü jätkab tööd. [Inventory.java](src/Inventory.java): `ArrayList<Product>` võimaldab tooteid lisada ja tsükliga läbi käia; `findByName()` otsib nime järgi järjest kõikide toodete hulgast. Väikese lao jaoks on see lihtne ja sobiv lahendus. |
| Praktiline programmeerimine | [Main.java](src/Main.java) meetodi `main()` menüü valikud 1–5 võimaldavad tooteid kuvada, lisada, kogust muuta, nime järgi otsida ja koguväärtust vaadata. Seal luuakse ka kolm algtoodet. [Inventory.java](src/Inventory.java) meetodid `addProduct()`, `findByName()` ja `getTotalValue()` teevad lao toiminguid. Tegeliku näidiskäivituse väljund on failis [kaivitus.txt](evidence/kaivitus.txt). |
| Iseseisev oskus | Selle README jaotis „Käivitamine” annab kompileerimise ja käivitamise käsud, „Neli OOP põhimõtet” selgitab koodi ning „Harjutus esitluseks” pakub väikese muudatuse ülesande. Oskust tuleb ise näidata: käivita programm, selgita valitud koodiridu ja tee õpetaja antud muudatus. |
| Töökorraldus | Kood on jagatud viide selge ülesandega klassi kaustas [src](src). Failide alguses on eesmärgi selgitused ja koodis eestikeelsed kommentaarid. Selle README tööpäevik kirjeldab arenduse järjekorda ning jaotis „Commit'id” viitab sisulistele arendusetappidele. Kogu ajalugu näeb käsuga `git log --oneline`. |

## Neli OOP põhimõtet

- **Abstraktsioon:** `Product` kirjeldab kõigile toodetele ühiseid andmeid ja
  toiminguid. See on abstraktne klass, seega `new Product(...)` pole lubatud.
  `getExtraInfo()` määrab, et iga konkreetne tooteliik peab oskama lisainfot anda.
- **Pärilus:** `FoodProduct extends Product` ja `ElectronicProduct extends Product`
  saavad nime, hinna, koguse ning nende meetodid vanemklassist. Ühist koodi ei pea kordama.
- **Kapseldamine:** `Product` väljad on `private`. Kogust muudetakse
  `changeQuantity()` kaudu, mis kontrollib uut väärtust. Nime ja hinda saab lugeda
  getteritega; need on selles lahenduses loomise järel muutumatud (`final`).
- **Polümorfism:** `Main` käib läbi `Product` tüüpi tooted ja kuvab need.
  `Product.toString()` kutsub `getExtraInfo()` meetodit. Java valib tegeliku objekti
  järgi toidu või elektroonika teostuse, kuigi muutuja tüüp on `Product`.

## Andmetüübid ja andmestruktuur

`String` hoiab nime, `int` täisarvulist kogust, `double` hinda ja koguväärtust ning
`boolean` menüü töötamise olekut. Õppeülesandes on `double` lihtne valik;
päris rahaarvestuses tasub kasutada `BigDecimal`-i või täisarvulisi sente.

`Inventory` hoiab tooteid `ArrayList<Product>`-is: väikeses poes on lihtne toode
`add()` abil lõppu lisada ja `for`-tsükliga kõik tooted läbi käia.
`findByName()` võrdleb järjest iga toote nime `equalsIgnoreCase()` abil ja tagastab
esimese vaste või `null`. Otsing on O(n), mis sobib väikese tootehulga jaoks.
`getProducts()` tagastab loendi koopia, et väljastpoolt ei saaks lao loendist
tooteid eemaldada. Koopias on endiselt samad tooteobjektid.

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
