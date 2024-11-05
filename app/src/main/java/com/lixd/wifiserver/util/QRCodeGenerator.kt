package com.lixd.wifiserver.util

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import java.util.Hashtable


object QRCodeGenerator {

    /**
     * @param content 二维码内容
     * @param width 二维码宽度
     * @param height 二维码高度
     * @param characterSet 编码方式(一般使用UTF-8)
     * @param errorCorrection 容错率 L：7% M：15% Q：25% H：35%
     * @param margin 空白边距（二维码与边框的空白区域)
     */
    fun generateQrCodeImage(
        content: String,
        width: Int = 200,
        height: Int = 200,
        characterSet: String = "utf-8",
        errorCorrection: String = "Q",
        margin: String = "0",
    ): Bitmap {
        /** 1.设置二维码相关配置,生成BitMatrix(位矩阵)对象 */
        val hints = Hashtable<EncodeHintType, String>()
        // 字符转码格式设置
        hints[EncodeHintType.CHARACTER_SET] = characterSet
        // 容错率设置
        hints[EncodeHintType.ERROR_CORRECTION] = errorCorrection
        hints[EncodeHintType.MARGIN] = margin
        /** 2.将配置参数传入到QRCodeWriter的encode方法生成BitMatrix(位矩阵)对象 */
        val bitMatrix = QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints)
        val pixels = IntArray(width * height)

        for (y in 0 until height) {
            for (x in 0 until width) {
                if (bitMatrix[x, y]) {
                    pixels[y * width + x] = Color.BLACK
                } else {
                    pixels[y * width + x] = Color.WHITE
                }
            }
        }
        // 生成二维码图片的格式，使用ARGB_8888
        val bitmap = Bitmap.createBitmap(
            width, height, Bitmap.Config.ARGB_8888
        )
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return bitmap
    }
}