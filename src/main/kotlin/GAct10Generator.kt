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
        val lines = file.readLines().toMutableList()
        syllabicNasalExtensionMap.forEach {
            lines.add("${it.key}\t${it.value}\n")
        }
        doubleVowelMapExtensionMap.forEach {
            lines.add("${it.key}\t${it.value}\n")
        }

        return lines.joinToString("\n")
    }

    /**
     * ローマ字（アルファベット）をひらがなに変換する。
     * ブルートフォースだが...再利用もしないのでとりあえずはこのままで。
     */
    private fun convertHiraganaFrom(romanAlphabet: String): String {
        when(romanAlphabet) {
            // 撥音拡張
            "CAN" -> return "かん"
            "CIN" -> return "きん"
            "CUN" -> return "くん"
            "CEN" -> return "けん"
            "CON" -> return "こん"
            "KAN" -> return "かん"
            "KIN" -> return "きん"
            "KUN" -> return "くん"
            "KEN" -> return "けん"
            "KON" -> return "こん"
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

            // 拗音+撥音
            "CYAN" -> return "きゃん"
            "CYIN" -> return "きぃん"
            "CYUN" -> return "きゅん"
            "CYEN" -> return "きぇん"
            "CYON" -> return "きょん"
            "KYAN" -> return "きゃん"
            "KYIN" -> return "きぃん"
            "KYUN" -> return "きゅん"
            "KYEN" -> return "きぇん"
            "KYON" -> return "きょん"
            "SYAN" -> return "しゃん"
            "SYIN" -> return "しぃん"
            "SYUN" -> return "しゅん"
            "SYEN" -> return "しぇん"
            "SYON" -> return "しょん"
            "TYAN" -> return "ちゃん"
            "TYIN" -> return "ちぃん"
            "TYUN" -> return "ちゅん"
            "TYEN" -> return "ちぇん"
            "TYON" -> return "ちょん"
            "NYAN" -> return "にゃん"
            "NYIN" -> return "にぃん"
            "NYUN" -> return "にゅん"
            "NYEN" -> return "にぇん"
            "NYON" -> return "にょん"
            "HYAN" -> return "ひゃん"
            "HYIN" -> return "ひぃん"
            "HYUN" -> return "ひゅん"
            "HYEN" -> return "ひぇん"
            "HYON" -> return "ひょん"
            "MYAN" -> return "みゃん"
            "MYIN" -> return "みぃん"
            "MYUN" -> return "みゅん"
            "MYEN" -> return "みぇん"
            "MYON" -> return "みょん"
            "RYAN" -> return "りゃん"
            "RYIN" -> return "りぃん"
            "RYUN" -> return "りゅん"
            "RYEN" -> return "りぇん"
            "RYON" -> return "りょん"
            "WYAN" -> return ""
            "WYIN" -> return ""
            "WYUN" -> return ""
            "WYEN" -> return ""
            "WYON" -> return ""
            "GYAN" -> return "ぎゃん"
            "GYIN" -> return "ぎぃん"
            "GYUN" -> return "ぎゅん"
            "GYEN" -> return "ぎぇん"
            "GYON" -> return "ぎょん"
            "ZYAN" -> return "じゃん"
            "ZYIN" -> return "じぃん"
            "ZYUN" -> return "じゅん"
            "ZYEN" -> return "じぇん"
            "ZYON" -> return "じょん"
            "DYAN" -> return "ぢゃん"
            "DYIN" -> return "ぢぃん"
            "DYUN" -> return "ぢゅん"
            "DYEN" -> return "ぢぇん"
            "DYON" -> return "ぢょん"
            "BYAN" -> return "びゃん"
            "BYIN" -> return "びぃん"
            "BYUN" -> return "びゅん"
            "BYEN" -> return "びぇん"
            "BYON" -> return "びょん"
            "PYAN" -> return "ぴゃん"
            "PYIN" -> return "ぴぃん"
            "PYUN" -> return "ぴゅん"
            "PYEN" -> return "ぴぇん"
            "PYON" -> return "ぴょん"

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

            // 二重母音拡張
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
            "YUI" -> return "ゆい"
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
            "PUU" -> return "ぷう"
            "PUI" -> return "ぷい"

            // 拗音+二重母音
            "CYAI" -> return "きゃい"
            "CYOU" -> return "きょう"
            "CYEI" -> return "きぇい"
            "CYUU" -> return "きゅう"
            "CYUI" -> return "きゅい"
            "KYAI" -> return "きゃい"
            "KYOU" -> return "きょう"
            "KYEI" -> return "きぇい"
            "KYUU" -> return "きゅう"
            "KYUI" -> return "きゅい"
            "SYAI" -> return "しゃい"
            "SYOU" -> return "しょう"
            "SYEI" -> return "しぇい"
            "SYUU" -> return "しゅう"
            "SYUI" -> return "しゅい"
            "TYAI" -> return "ちゃい"
            "TYOU" -> return "ちょう"
            "TYEI" -> return "ちぇい"
            "TYUU" -> return "ちゅう"
            "TYUI" -> return "ちゅい"
            "NYAI" -> return "にゃい"
            "NYOU" -> return "にょう"
            "NYEI" -> return "にぇい"
            "NYUU" -> return "にゅう"
            "NYUI" -> return "にゅい"
            "HYAI" -> return "ひゃい"
            "HYOU" -> return "ひょう"
            "HYEI" -> return "ひぇい"
            "HYUU" -> return "ひゅう"
            "HYUI" -> return "ひゅい"
            "MYAI" -> return "みゃい"
            "MYOU" -> return "みょう"
            "MYEI" -> return "みぇい"
            "MYUU" -> return "みゅう"
            "MYUI" -> return "みゅい"
            "RYAI" -> return "りゃい"
            "RYOU" -> return "りょう"
            "RYEI" -> return "りぇい"
            "RYUU" -> return "りゅう"
            "RYUI" -> return "りゅい"
            "WYAI" -> return ""
            "WYOU" -> return ""
            "WYEI" -> return ""
            "WYUU" -> return ""
            "WYUI" -> return ""
            "GYAI" -> return "ぎゃい"
            "GYOU" -> return "ぎょう"
            "GYEI" -> return "ぎぇい"
            "GYUU" -> return "ぎゅう"
            "GYUI" -> return "ぎゅい"
            "ZYAI" -> return "じゃい"
            "ZYOU" -> return "じょう"
            "ZYEI" -> return "じぇい"
            "ZYUU" -> return "じゅう"
            "ZYUI" -> return "じゅい"
            "DYAI" -> return "ぢゃい"
            "DYOU" -> return "ぢょう"
            "DYEI" -> return "ぢぇい"
            "DYUU" -> return "ぢゅう"
            "DYUI" -> return "ぢゅい"
            "BYAI" -> return "びゃい"
            "BYOU" -> return "びょう"
            "BYEI" -> return "びぇい"
            "BYUU" -> return "びゅう"
            "BYUI" -> return "びゅい"
            "PYAI" -> return "ぴゃい"
            "PYOU" -> return "ぴょう"
            "PYEI" -> return "ぴぇい"
            "PYUU" -> return "ぴゅう"
            "PYUI" -> return "ぴゅい"

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

            else -> return ""
        }
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