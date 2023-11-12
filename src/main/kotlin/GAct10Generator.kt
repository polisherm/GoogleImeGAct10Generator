import java.io.File

/**
 * gAct10の生成器
 */
class GAct10Generator {
    /**
     * gAct10のローマ字マップを生成する。
     *
     * @param file 既存のローマ字マップファイル
     * @return gAct10のローマ字マップ
     */
    fun generate(file: File): String {
        // 母音と子音の単純組み合わせのキーとそれが出力するひらがなを全部作成してマップに格納する。
        val vowelAndConsonantMap = mutableMapOf<String, String>()
        Vowel.values().forEach { vowel ->
            Consonant.values().forEach { consonant ->
                val key = "${consonant.key}${vowel.key}"
                val value = convertHiraganaFrom("${consonant.name}${vowel.name}")
                vowelAndConsonantMap[key] = value
            }
        }

        // 撥音拡張したキーとそれが出力するひらがなを全部作成してマップに格納する。
        val syllabicNasalExtensionMap = mutableMapOf<String, String>()
        SyllabicNasalExtension.entries.forEach { syllabicNasalExtension ->
            Consonant.entries.forEach { consonant ->
                val key = "${consonant.key}${syllabicNasalExtension.key}"
                val value = convertHiraganaFrom("${consonant.name}${syllabicNasalExtension.name}")
                syllabicNasalExtensionMap[key] = value
            }
        }

        // 二重母音拡張したキーとそれが出力するひらがなを全部作成してマップに格納する。
        val doubleVowelMapExtensionMap = mutableMapOf<String, String>()
        DoubleVowelExtension.entries.forEach { doubleVowelExtension ->
            Consonant.entries.forEach { consonant ->
                val key = "${consonant.key}${doubleVowelExtension.key}"
                val value = convertHiraganaFrom("${consonant.name}${doubleVowelExtension.name}")
                doubleVowelMapExtensionMap[key] = value
            }
        }

        // ロードしたファイルの内容 + 拡張キーを結果として返す。
        // NOTE:
        //  既存のファイルにキーマップを追加する人は上、既存のファイルは捨ててこのツールで生成したものを使う人は下の処理を使用
        val lines = file.readLines().toMutableList()  // 既存のファイルにキーマップを追加する。
//        val lines = mutableListOf<String>() // 既存のファイルは読み込まない。
        vowelAndConsonantMap.forEach {
            // value(キーに対応する実際の入力)が空の場合はスキップ。
            val addedText = "${it.key}\t${it.value}"
            if (it.value.isNotBlank()) {
                lines.add(addedText)
            }
        }
        syllabicNasalExtensionMap.forEach {
            // value(キーに対応する実際の入力)が空の場合はスキップ。
            // eg) whan -> "" なのでスキップ
            val addedText = "${it.key}\t${it.value}"
            if (it.value.isNotBlank()) {
                lines.add(addedText)
            }
        }
        doubleVowelMapExtensionMap.forEach {
            // value(キーに対応する実際の入力)が空の場合はスキップ。
            val addedText = "${it.key}\t${it.value}"
            if (it.value.isNotBlank()) {
                lines.add(addedText)
            }
        }

        // ここで重複したローマ字マップを削除しつつ、改行で結合して返す。
        return lines.distinct().joinToString("\n")
    }

    /**
     * ローマ字（アルファベット）をひらがなに変換する。
     * ブルートフォースだが...再利用もしないのでとりあえずはこのままで。
     */
    private fun convertHiraganaFrom(romanAlphabet: String): String {
        when(romanAlphabet) {
            // 子音+母音
            "CA" -> return "か"
            "CI" -> return "き"
            "CU" -> return "く"
            "CE" -> return "け"
            "CO" -> return "こ"
            "KA" -> return "か"
            "KI" -> return "き"
            "KU" -> return "く"
            "KE" -> return "け"
            "KO" -> return "こ"
            "SA" -> return "さ"
            "SI" -> return "し"
            "SU" -> return "す"
            "SE" -> return "せ"
            "SO" -> return "そ"
            "TA" -> return "た"
            "TI" -> return "ち"
            "TU" -> return "つ"
            "TE" -> return "て"
            "TO" -> return "と"
            "NA" -> return "な"
            "NI" -> return "に"
            "NU" -> return "ぬ"
            "NE" -> return "ね"
            "NO" -> return "の"
            "HA" -> return "は"
            "HI" -> return "ひ"
            "HU" -> return "ふ"
            "HE" -> return "へ"
            "HO" -> return "ほ"
            "MA" -> return "ま"
            "MI" -> return "み"
            "MU" -> return "む"
            "ME" -> return "め"
            "MO" -> return "も"
            "YA" -> return "や"
            "YI" -> return ""
            "YU" -> return "ゆ"
            "YE" -> return ""
            "YO" -> return "よ"
            "RA" -> return "ら"
            "RI" -> return "り"
            "RU" -> return "る"
            "RE" -> return "れ"
            "RO" -> return "ろ"
            "WA" -> return "わ"
            "WI" -> return "うぃ"
            "WU" -> return ""
            "WE" -> return "うぇ"
            "WO" -> return "を"
            "GA" -> return "が"
            "GI" -> return "ぎ"
            "GU" -> return "ぐ"
            "GE" -> return "げ"
            "GO" -> return "ご"
            "ZA" -> return "ざ"
            "ZI" -> return "じ"
            "ZU" -> return "ず"
            "ZE" -> return "ぜ"
            "ZO" -> return "ぞ"
            "DA" -> return "だ"
            "DI" -> return "ぢ"
            "DU" -> return "づ"
            "DE" -> return "で"
            "DO" -> return "ど"
            "BA" -> return "ば"
            "BI" -> return "び"
            "BU" -> return "ぶ"
            "BE" -> return "べ"
            "BO" -> return "ぼ"
            "PA" -> return "ぱ"
            "PI" -> return "ぴ"
            "PU" -> return "ぷ"
            "PE" -> return "ぺ"
            "PO" -> return "ぽ"
            "VA" -> return "ゔぁ"
            "VI" -> return "ゔぃ"
            "VU" -> return "ゔ"
            "VE" -> return "ゔぇ"
            "VO" -> return "ゔぉ"

            // 拗音+母音
            // 拗音は拡張ですべて処理するため、通常の拗音キー（y）は使わない。
            // `y`は二重母音拡張で予約されているのも理由。
            "KYA" -> return ""
            "KYU" -> return ""
            "KYO" -> return ""
            "CYA" -> return ""
            "CYU" -> return ""
            "CYO" -> return ""
            "SYA" -> return ""
            "SYU" -> return ""
            "SYO" -> return ""
            "TYA" -> return ""
            "TYU" -> return ""
            "TYO" -> return ""
            "NYA" -> return ""
            "NYU" -> return ""
            "NYO" -> return ""
            "HYA" -> return ""
            "HYU" -> return ""
            "HYO" -> return ""
            "MYA" -> return ""
            "MYU" -> return ""
            "MYO" -> return ""
            "RYA" -> return ""
            "RYU" -> return ""
            "RYO" -> return ""
            "GYA" -> return ""
            "GYU" -> return ""
            "GYO" -> return ""
            "ZYA" -> return ""
            "ZYU" -> return ""
            "ZYO" -> return ""
            "BYA" -> return ""
            "BYU" -> return ""
            "BYO" -> return ""
            "PYA" -> return ""
            "PYU" -> return ""
            "PYO" -> return ""

            // 拗音拡張+母音
            "CHA" -> return "きゃ"
            "CHI" -> return "きぃ"
            "CHU" -> return "きゅ"
            "CHE" -> return "きぇ"
            "CHO" -> return "きょ"
            "KHA" -> return "きゃ"
            "KHI" -> return "きぃ"
            "KHU" -> return "きゅ"
            "KHE" -> return "きぇ"
            "KHO" -> return "きょ"
            "SHA" -> return "しゃ"
            "SHI" -> return "し"
            "SHU" -> return "しゅ"
            "SHE" -> return "しぇ"
            "SHO" -> return "しょ"
            "THA" -> return "ちゃ"
            "THI" -> return "ちぃ"
            "THU" -> return "ちゅ"
            "THE" -> return "ちぇ"
            "THO" -> return "ちょ"
            "NHA" -> return "にゃ"
            "NHI" -> return "にぃ"
            "NHU" -> return "にゅ"
            "NHE" -> return "にぇ"
            "NHO" -> return "にょ"
            "HNA" -> return "ひゃ"
            "HNI" -> return "ひぃ"
            "HNU" -> return "ひゅ"
            "HNE" -> return "ひぇ"
            "HNO" -> return "ひょ"
            "MNA" -> return "みゃ"
            "MNI" -> return "みぃ"
            "MNU" -> return "みゅ"
            "MNE" -> return "みぇ"
            "MNO" -> return "みょ"
            "YHA" -> return ""
            "YHI" -> return ""
            "YHU" -> return ""
            "YHE" -> return ""
            "YHO" -> return ""
            "RHA" -> return "りゃ"
            "RHI" -> return "りぃ"
            "RHU" -> return "りゅ"
            "RHE" -> return "りぇ"
            "RHO" -> return "りょ"
            "WHA" -> return ""
            "WHI" -> return ""
            "WHU" -> return ""
            "WHE" -> return ""
            "WHO" -> return "うぉ"
            "GNA" -> return "ぎゃ"
            "GNI" -> return "ぎぃ"
            "GNU" -> return "ぎゅ"
            "GNE" -> return "ぎぇ"
            "GNO" -> return "ぎょ"
            "ZHA" -> return "じゃ"
            "ZHI" -> return "じぃ"
            "ZHU" -> return "じゅ"
            "ZHE" -> return "じぇ"
            "ZHO" -> return "じょ"
            "JA" -> return "じゃ"
            "JU" -> return "じゅ"
            "JO" -> return "じょ"
            "DNA" -> return "ぢゃ"
            "DNI" -> return "ぢぃ"
            "DNU" -> return "ぢゅ"
            "DNE" -> return "ぢぇ"
            "DNO" -> return "ぢょ"
            "BNA" -> return "びゃ"
            "BNI" -> return "びぃ"
            "BNU" -> return "びゅ"
            "BNE" -> return "びぇ"
            "BNO" -> return "びょ"
            "PHA" -> return "ぴゃ"
            "PHI" -> return "ぴぃ"
            "PHU" -> return "ぴゅ"
            "PHE" -> return "ぴぇ"
            "PHO" -> return "ぴょ"
            "VHA" -> return "ゔゃ"
            "VHI" -> return ""
            "VHU" -> return "ゔゅ"
            "VHE" -> return ""
            "VHO" -> return "ゔょ"

            // 撥音拡張
            "CAN" -> return "かん"
            "CIN" -> return "きん"
            "CUN" -> return "くん"
            "CEN" -> return "けん"
            "CON" -> return "こん"
            "KAN" -> return ""  // Kの代わりにCを使う。Kの拡張は行わない。
            "KIN" -> return ""
            "KUN" -> return ""
            "KEN" -> return ""
            "KON" -> return ""
            "SAN" -> return "さん"
            "SIN" -> return "しん"
            "SUN" -> return "すん"
            "SEN" -> return "せん"
            "SON" -> return "そん"
            "TAN" -> return "たん"
            "TIN" -> return "ちん"
            "TUN" -> return "つん"
            "TEN" -> return "てん"
            "TON" -> return "とん"
            "NAN" -> return "なん"
            "NIN" -> return "にん"
            "NUN" -> return "ぬん"
            "NEN" -> return "ねん"
            "NON" -> return "のん"
            "HAN" -> return "はん"
            "HIN" -> return "ひん"
            "HUN" -> return "ふん"
            "HEN" -> return "へん"
            "HON" -> return "ほん"
            "MAN" -> return "まん"
            "MIN" -> return "みん"
            "MUN" -> return "むん"
            "MEN" -> return "めん"
            "MON" -> return "もん"
            "YAN" -> return "やん"
            "YIN" -> return ""
            "YUN" -> return "ゆん"
            "YEN" -> return ""
            "YON" -> return "よん"
            "RAN" -> return "らん"
            "RIN" -> return "りん"
            "RUN" -> return "るん"
            "REN" -> return "れん"
            "RON" -> return "ろん"
            "WAN" -> return "わん"
            "WIN" -> return "うぃん"
            "WUN" -> return ""
            "WEN" -> return "うぇん"
            "WON" -> return ""
            "GAN" -> return "がん"
            "GIN" -> return "ぎん"
            "GUN" -> return "ぐん"
            "GEN" -> return "げん"
            "GON" -> return "ごん"
            "ZAN" -> return "ざん"
            "ZIN" -> return "じん"
            "ZUN" -> return "ずん"
            "ZEN" -> return "ぜん"
            "ZON" -> return "ぞん"
            "DAN" -> return "だん"
            "DIN" -> return "ぢん"
            "DUN" -> return "づん"
            "DEN" -> return "でん"
            "DON" -> return "どん"
            "BAN" -> return "ばん"
            "BIN" -> return "びん"
            "BUN" -> return "ぶん"
            "BEN" -> return "べん"
            "BON" -> return "ぼん"
            "PAN" -> return "ぱん"
            "PIN" -> return "ぴん"
            "PUN" -> return "ぷん"
            "PEN" -> return "ぺん"
            "PON" -> return "ぽん"
            "VAN" -> return "う゛ぁん"
            "VIN" -> return "う゛ぃん"
            "VUN" -> return "う゛ん"
            "VEN" -> return "う゛ぇん"
            "VON" -> return "う゛ぉん"
            "JAN" -> return "じゃん"
            "JIN" -> return "じん"
            "JUN" -> return "じゅん"
            "JEN" -> return "じぇん"
            "JON" -> return "じょん"

            // 拗音+撥音
            // `y`は二重母音で使用されているので、通常のyを使う拗音は使用不可とする。
            "CYAN" -> return ""
            "CYIN" -> return ""
            "CYUN" -> return ""
            "CYEN" -> return ""
            "CYON" -> return ""
            "KYAN" -> return ""
            "KYIN" -> return ""
            "KYUN" -> return ""
            "KYEN" -> return ""
            "KYON" -> return ""
            "SYAN" -> return ""
            "SYIN" -> return ""
            "SYUN" -> return ""
            "SYEN" -> return ""
            "SYON" -> return ""
            "TYAN" -> return ""
            "TYIN" -> return ""
            "TYUN" -> return ""
            "TYEN" -> return ""
            "TYON" -> return ""
            "NYAN" -> return ""
            "NYIN" -> return ""
            "NYUN" -> return ""
            "NYEN" -> return ""
            "NYON" -> return ""
            "HYAN" -> return ""
            "HYIN" -> return ""
            "HYUN" -> return ""
            "HYEN" -> return ""
            "HYON" -> return ""
            "MYAN" -> return ""
            "MYIN" -> return ""
            "MYUN" -> return ""
            "MYEN" -> return ""
            "MYON" -> return ""
            "RYAN" -> return ""
            "RYIN" -> return ""
            "RYUN" -> return ""
            "RYEN" -> return ""
            "RYON" -> return ""
            "WYAN" -> return ""
            "WYIN" -> return ""
            "WYUN" -> return ""
            "WYEN" -> return ""
            "WYON" -> return ""
            "GYAN" -> return ""
            "GYIN" -> return ""
            "GYUN" -> return ""
            "GYEN" -> return ""
            "GYON" -> return ""
            "ZYAN" -> return ""
            "ZYIN" -> return ""
            "ZYUN" -> return ""
            "ZYEN" -> return ""
            "ZYON" -> return ""
            "DYAN" -> return ""
            "DYIN" -> return ""
            "DYUN" -> return ""
            "DYEN" -> return ""
            "DYON" -> return ""
            "BYAN" -> return ""
            "BYIN" -> return ""
            "BYUN" -> return ""
            "BYEN" -> return ""
            "BYON" -> return ""
            "PYAN" -> return ""
            "PYIN" -> return ""
            "PYUN" -> return ""
            "PYEN" -> return ""
            "PYON" -> return ""

            // 拗音拡張 + 撥音拡張
            "CHAN" -> return "きゃん"
            "CHIN" -> return "きぃん"
            "CHUN" -> return "きゅん"
            "CHEN" -> return "きぇん"
            "CHON" -> return "きょん"
            "KHAN" -> return "きゃん"
            "KHIN" -> return "きぃん"
            "KHUN" -> return "きゅん"
            "KHEN" -> return "きぇん"
            "KHON" -> return "きょん"
            "SHAN" -> return "しゃん"
            "SHIN" -> return "しん"   // 拗音を付けない
            "SHUN" -> return "しゅん"
            "SHEN" -> return "しぇん"
            "SHON" -> return "しょん"
            "THAN" -> return "ちゃん"
            "THIN" -> return "ちぃん"
            "THUN" -> return "ちゅん"
            "THEN" -> return "ちぇん"
            "THON" -> return "ちょん"
            "NHAN" -> return "にゃん"
            "NHIN" -> return "にぃん"
            "NHUN" -> return "にゅん"
            "NHEN" -> return "にぇん"
            "NHON" -> return "にょん"
            "HNAN" -> return "ひゃん"
            "HNIN" -> return "ひぃん"
            "HNUN" -> return "ひゅん"
            "HNEN" -> return "ひぇん"
            "HNON" -> return "ひょん"
            "MNAN" -> return "みゃん"
            "MNIN" -> return "みぃん"
            "MNUN" -> return "みゅん"
            "MNEN" -> return "みぇん"
            "MNON" -> return "みょん"
            "YHAN" -> return ""
            "YHIN" -> return ""
            "YHUN" -> return ""
            "YHEN" -> return ""
            "YHON" -> return ""
            "RHAN" -> return "りゃん"
            "RHIN" -> return "りぃん"
            "RHUN" -> return "りゅん"
            "RHEN" -> return "りぇん"
            "RHON" -> return "りょん"
            "WHAN" -> return ""
            "WHIN" -> return ""
            "WHUN" -> return ""
            "WHEN" -> return ""
            "WHON" -> return "うぉん"
            "GNAN" -> return "ぎゃん"
            "GNIN" -> return "ぎぃん"
            "GNUN" -> return "ぎゅん"
            "GNEN" -> return "ぎぇん"
            "GNON" -> return "ぎょん"
            "ZHAN" -> return "じゃん"
            "ZHIN" -> return "じぃん"
            "ZHUN" -> return "じゅん"
            "ZHEN" -> return "じぇん"
            "ZHON" -> return "じょん"
            "DNAN" -> return "ぢゃん"
            "DNIN" -> return "ぢぃん"
            "DNUN" -> return "ぢゅん"
            "DNEN" -> return "ぢぇん"
            "DNON" -> return "ぢょん"
            "BNAN" -> return "びゃん"
            "BNIN" -> return "びぃん"
            "BNUN" -> return "びゅん"
            "BNEN" -> return "びぇん"
            "BNON" -> return "びょん"
            "PHAN" -> return "ぴゃん"
            "PHIN" -> return "ぴぃん"
            "PHUN" -> return "ぴゅん"
            "PHEN" -> return "ぴぇん"
            "PHON" -> return "ぴょん"
            "VHAN" -> return "う゛ゃん"
            "VHIN" -> return ""
            "VHUN" -> return "う゛ゅん"
            "VHEN" -> return ""
            "VHON" -> return "う゛ょん"

            // 二重母音拡張
            "CAI" -> return "かい"
            "COU" -> return "こう"
            "CEI" -> return "けい"
            "CUU" -> return "くう"
            "CUI" -> return "くい"
            "KAI" -> return "かい"
            "KOU" -> return "こう"
            "KEI" -> return "けい"
            "KUU" -> return "くう"
            "KUI" -> return "くい"
            "SAI" -> return "さい"
            "SOU" -> return "そう"
            "SEI" -> return "せい"
            "SUU" -> return "すう"
            "SUI" -> return "すい"
            "TAI" -> return "たい"
            "TOU" -> return "とう"
            "TEI" -> return "てい"
            "TUU" -> return "つう"
            "TUI" -> return "つい"
            "NAI" -> return "ない"
            "NOU" -> return "のう"
            "NEI" -> return "ねい"
            "NUU" -> return "ぬう"
            "NUI" -> return "ぬい"
            "HAI" -> return "はい"
            "HOU" -> return "ほう"
            "HEI" -> return "へい"
            "HUU" -> return "ふう"
            "HUI" -> return "ふい"
            "MAI" -> return "まい"
            "MOU" -> return "もう"
            "MEI" -> return "めい"
            "MUU" -> return "むう"
            "MUI" -> return "むい"
            "YAI" -> return "やい"
            "YOU" -> return "よう"
            "YEI" -> return "いぇい"
            "YUU" -> return "ゆう"
            "YUI" -> return ""  // `yy`という入力になってしまうが、yyは「っや」という入力で予約されているので空白に。
            "RAI" -> return "らい"
            "ROU" -> return "ろう"
            "REI" -> return "れい"
            "RUU" -> return "るう"
            "RUI" -> return "るい"
            "WAI" -> return "わい"
            "WOU" -> return "うぉう"
            "WEI" -> return "うぇい"
            "WUU" -> return ""
            "WUI" -> return ""
            "GAI" -> return "がい"
            "GOU" -> return "ごう"
            "GEI" -> return "げい"
            "GUU" -> return "ぐう"
            "GUI" -> return "ぐい"
            "ZAI" -> return "ざい"
            "ZOU" -> return "ぞう"
            "ZEI" -> return "ぜい"
            "ZUU" -> return "ずう"
            "ZUI" -> return "ずい"
            "DAI" -> return "だい"
            "DOU" -> return "どう"
            "DEI" -> return "でい"
            "DUU" -> return "づう"
            "DUI" -> return "づい"
            "BAI" -> return "ばい"
            "BOU" -> return "ぼう"
            "BEI" -> return "べい"
            "BUU" -> return "ぶう"
            "BUI" -> return "ぶい"
            "PAI" -> return "ぱい"
            "POU" -> return "ぽう"
            "PEI" -> return "ぺい"
            "PUU" -> return ""  // ここで"ぷう"と出力するようにしてしまうと、「ップ」という促音入りの文字が出力できなくなる。
            "PUI" -> return "ぷい"
            "VAI" -> return "ゔぁい"
            "VOU" -> return "ゔぉう"
            "VEI" -> return "ゔぇい"
            "VUU" -> return ""
            "VUI" -> return ""
            "JAI" -> return "じゃい"
            "JOU" -> return "じょう"
            "JEI" -> return "じぇい"
            "JUU" -> return "じゅう"
            "JUI" -> return "じゅい"

            // 拗音+二重母音
            // `y`は二重母音で使用されているので、通常のyを使う拗音は使用不可とする。
            "CYAI" -> return ""
            "CYOU" -> return ""
            "CYEI" -> return ""
            "CYUU" -> return ""
            "CYUI" -> return ""
            "KYAI" -> return ""
            "KYOU" -> return ""
            "KYEI" -> return ""
            "KYUU" -> return ""
            "KYUI" -> return ""
            "SYAI" -> return ""
            "SYOU" -> return ""
            "SYEI" -> return ""
            "SYUU" -> return ""
            "SYUI" -> return ""
            "TYAI" -> return ""
            "TYOU" -> return ""
            "TYEI" -> return ""
            "TYUU" -> return ""
            "TYUI" -> return ""
            "NYAI" -> return ""
            "NYOU" -> return ""
            "NYEI" -> return ""
            "NYUU" -> return ""
            "NYUI" -> return ""
            "HYAI" -> return ""
            "HYOU" -> return ""
            "HYEI" -> return ""
            "HYUU" -> return ""
            "HYUI" -> return ""
            "MYAI" -> return ""
            "MYOU" -> return ""
            "MYEI" -> return ""
            "MYUU" -> return ""
            "MYUI" -> return ""
            "RYAI" -> return ""
            "RYOU" -> return ""
            "RYEI" -> return ""
            "RYUU" -> return ""
            "RYUI" -> return ""
            "WYAI" -> return ""
            "WYOU" -> return ""
            "WYEI" -> return ""
            "WYUU" -> return ""
            "WYUI" -> return ""
            "GYAI" -> return ""
            "GYOU" -> return ""
            "GYEI" -> return ""
            "GYUU" -> return ""
            "GYUI" -> return ""
            "ZYAI" -> return ""
            "ZYOU" -> return ""
            "ZYEI" -> return ""
            "ZYUU" -> return ""
            "ZYUI" -> return ""
            "DYAI" -> return ""
            "DYOU" -> return ""
            "DYEI" -> return ""
            "DYUU" -> return ""
            "DYUI" -> return ""
            "BYAI" -> return ""
            "BYOU" -> return ""
            "BYEI" -> return ""
            "BYUU" -> return ""
            "BYUI" -> return ""
            "PYAI" -> return ""
            "PYOU" -> return ""
            "PYEI" -> return ""
            "PYUU" -> return ""
            "PYUI" -> return ""

            // 拗音拡張 + 二重母音拡張
            "CHAI" -> return "きゃい"
            "CHOU" -> return "きょう"
            "CHEI" -> return "きぇい"
            "CHUU" -> return "きゅう"
            "CHUI" -> return "きゅい"
            "KHAI" -> return "きゃい"
            "KHOU" -> return "きょう"
            "KHEI" -> return "きぇい"
            "KHUU" -> return "きゅう"
            "KHUI" -> return "きゅい"
            "SHAI" -> return "しゃい"
            "SHOU" -> return "しょう"
            "SHEI" -> return "しぇい"
            "SHUU" -> return "しゅう"
            "SHUI" -> return "しゅい"
            "THAI" -> return "ちゃい"
            "THOU" -> return "ちょう"
            "THEI" -> return "ちぇい"
            "THUU" -> return "ちゅう"
            "THUI" -> return "ちゅい"
            "NHAI" -> return "にゃい"
            "NHOU" -> return "にょう"
            "NHEI" -> return "にぇい"
            "NHUU" -> return "にゅう"
            "NHUI" -> return "にゅい"
            "HNAI" -> return "ひゃい"
            "HNOU" -> return "ひょう"
            "HNEI" -> return "ひぇい"
            "HNUU" -> return "ひゅう"
            "HNUI" -> return "ひゅい"
            "MNAI" -> return "みゃい"
            "MNOU" -> return "みょう"
            "MNEI" -> return "みぇい"
            "MNUU" -> return "みゅう"
            "MNUI" -> return "みゅい"
            "YHAI" -> return ""
            "YHOU" -> return ""
            "YHEI" -> return ""
            "YHUU" -> return ""
            "YHUI" -> return ""
            "RHAI" -> return "りゃい"
            "RHOU" -> return "りょう"
            "RHEI" -> return "りぇい"
            "RHUU" -> return "りゅう"
            "RHUI" -> return "りゅい"
            "WHAI" -> return ""
            "WHOU" -> return "うぉう"
            "WHEI" -> return "うぇい"
            "WHUU" -> return ""
            "WHUI" -> return ""
            "GNAI" -> return "ぎゃい"
            "GNOU" -> return "ぎょう"
            "GNEI" -> return "ぎぇい"
            "GNUU" -> return "ぎゅう"
            "GNUI" -> return "ぎゅい"
            "ZHAI" -> return "じゃい"
            "ZHOU" -> return "じょう"
            "ZHEI" -> return "じぇい"
            "ZHUU" -> return "じゅう"
            "ZHUI" -> return "じゅい"
            "DNAI" -> return "ぢゃい"
            "DNOU" -> return "ぢょう"
            "DNEI" -> return "ぢぇい"
            "DNUU" -> return "ぢゅう"
            "DNUI" -> return "ぢゅい"
            "BNAI" -> return "びゃい"
            "BNOU" -> return "びょう"
            "BNEI" -> return "びぇい"
            "BNUU" -> return "びゅう"
            "BNUI" -> return "びゅい"
            "PHAI" -> return "ぴゃい"
            "PHOU" -> return "ぴょう"
            "PHEI" -> return "ぴぇい"
            "PHUU" -> return "ぴゅう"
            "PHUI" -> return "ぴゅい"
            "VHAI" -> return "う゛ゃい"
            "VHOU" -> return "う゛ょう"
            "VHEI" -> return "う゛ぇい"
            "VHUU" -> return "う゛ゅう"
            "VHUI" -> return "う゛ゅい"

            else -> return ""
        }
    }

    /**
     * 母音の列挙体
     * 対応するキーを引数として設定
     */
    private enum class Vowel(val key: String) {
        A("a"),
        I("i"),
        U("u"),
        E("e"),
        O("o");
    }

    /**
     * 子音・拗音の列挙体
     * 対応するキーを引数として設定
     */
    private enum class Consonant(val key: String) {
        // 子音
        C("c"), // c==kとみなす。
        K("k"),
        S("s"),
        T("t"),
        N("n"),
        H("h"),
        M("m"),
        Y("y"),
        R("r"),
        W("w"),
        G("g"),
        Z("z"),
        D("d"),
        B("b"),
        P("p"),
        V("v"), // 若干例外的だがVも追加
        J("j"),

        // 拗音
        CY("cy"),
        KY("ky"),
        SY("sy"),
        TY("ty"),
        NY("ny"),
        HY("hy"),
        MY("my"),
        RY("ry"),
        WY("wy"),
        GY("gy"),
        ZY("zy"),
        DY("dy"),
        BY("by"),
        PY("py"),

        // 拗音拡張
        CH("ch"),
        KH("kh"),
        SH("sh"),
        TH("th"),
        NH("nh"),
        HN("hn"),
        MN("mn"),
        YH("yh"),
        RH("rh"),
        WH("wh"),
        GN("gn"),
        ZH("zh"),
        DN("dn"),
        BN("bn"),
        PH("ph");
    }

    /**
     * 撥音拡張の列挙体
     * 対応するキーを引数として設定
     */
    private enum class SyllabicNasalExtension(val key: String) {
        AN(";"),
        ON("q"),
        EN("j"),
        UN("k"),
        IN("x");
    }

    /**
     * 二重母音拡張の列挙体
     * 対応するキーを引数として設定
     */
    private enum class DoubleVowelExtension(val key: String) {
        AI("'"),
        OU(","),
        EI("."),
        UU("p"),
        UI("y");
    }
}