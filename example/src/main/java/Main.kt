import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import java.io.FileInputStream
import java.io.FileOutputStream

/**
 *
 * Author: Seagazer
 * Date: 2020/11/11
 */
class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val targetFile = "${System.getProperty("user.dir")}/example/src/main/java/Target.class"
            println("目标文件:$targetFile")
            val startTime = System.currentTimeMillis()
            val fis = FileInputStream(targetFile)
            val reader = ClassReader(fis)
            val writer = ClassWriter(ClassWriter.COMPUTE_FRAMES)
            println("开始修改文件")
            reader.accept(HelloClassVisitor(writer), ClassReader.EXPAND_FRAMES)
            val bytes = writer.toByteArray()
            val fos = FileOutputStream(targetFile)
            println("写入文件:$targetFile")
            fos.write(bytes)
            fos.flush()
            println("关闭文件流")
            fis.close()
            fos.close()
            println("本次插桩耗时:${System.currentTimeMillis() - startTime} ms")
        }
    }
}