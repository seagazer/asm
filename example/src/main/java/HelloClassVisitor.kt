import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes


/**
 *
 * Author: Seagazer
 * Date: 2020/11/11
 */
class HelloClassVisitor(visitor: ClassVisitor) : ClassVisitor(Opcodes.ASM7, visitor) {

    override fun visitMethod(access: Int, name: String?, descriptor: String?, signature: String?, exceptions: Array<out String>?): MethodVisitor {
        val visitMethod = super.visitMethod(access, name, descriptor, signature, exceptions)
        println("访问方法:$name, 描述符:$descriptor")
        return HelloMethodVisitor(visitMethod, access, name, descriptor)
    }
}