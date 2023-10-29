import java.io.File
import javax.swing.JButton
import javax.swing.JFileChooser
import javax.swing.JFrame
import javax.swing.JOptionPane

/**
 * Google日本語入力でロードできるgAct10のローマ字マップを生成する。
 * gAct10 -> http://hp.vector.co.jp/authors/VA002116/gact/gact10_doc.html
 */
class GeneratorApp: JFrame() {
    private val openButton = JButton("開く").apply {
        addActionListener {
            // ファイル選択ダイアログを表示
            val fileChooser = JFileChooser()
            val selected = fileChooser.showOpenDialog(this@GeneratorApp)
            if (selected == JFileChooser.APPROVE_OPTION) {
                // 選択されたファイルを取得
                val file = fileChooser.selectedFile

                // ファイルを読み込み、変換処理を実行
                val generatedString = GAct10Generator().generate(file)

                // ファイル保存ダイアログを表示
                val saveFileChooser = object : JFileChooser() {
                    override fun approveSelection() {
                        // もし拡張子なしでパスが指定されたらここで拡張子（.txt）を付与する。
                        if (selectedFile.extension.isEmpty()) {
                            selectedFile = File("${selectedFile.absolutePath}.txt")
                        }

                        if (selectedFile.exists() && dialogType == SAVE_DIALOG) {
                            val result = JOptionPane.showConfirmDialog(this, "同名ファイルが存在します。上書きしますか？", "確認", JOptionPane.YES_NO_CANCEL_OPTION)
                            when (result) {
                                JOptionPane.YES_OPTION -> super.approveSelection()
                                JOptionPane.NO_OPTION -> return
                                JOptionPane.CLOSED_OPTION -> return
                                JOptionPane.CANCEL_OPTION -> cancelSelection()
                            }
                        }

                        super.approveSelection()
                    }
                }
                val saveSelected = saveFileChooser.showSaveDialog(this@GeneratorApp)
                if (saveSelected == JFileChooser.APPROVE_OPTION) {
                    val saveFile = saveFileChooser.selectedFile
                    saveFile.writeText(generatedString)
                }
            }
        }
    }

    init {
        // メインフレームの設定
        title = "Google日本語入力用 gACT10ローマ字マップジェネレーター"
        setSize(400, 300)
        setLocationRelativeTo(null)
        defaultCloseOperation = EXIT_ON_CLOSE

        add(openButton)
    }
}