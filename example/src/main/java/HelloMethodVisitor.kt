import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.objectweb.asm.commons.AdviceAdapter
import org.objectweb.asm.commons.Method

/**
 *
 * Author: Seagazer
 * Date: 2020/11/11
 */
class HelloMethodVisitor(methodVisitor: MethodVisitor, access: Int, name: String?, descriptor: String?) :
        AdviceAdapter(Opcodes.ASM7, methodVisitor, access, name, descriptor) {
    private var isInjectHello = false

    override fun visitAnnotation(descriptor: String?, visible: Boolean): AnnotationVisitor {
        println("访问方法:$name -注解:$descriptor")
        if (descriptor!! == Type.getDescriptor(InjectHello::class.java)) {
            println("标记了注解:$descriptor, 需要处理")
            isInjectHello = true
        }
        return super.visitAnnotation(descriptor, visible)
    }

    /**
     * ==========================>
     * Java方法：
     *       private void testAsm() {
     *            System.out.println("Hello Asm");// 准备插入的代码
     *       }
     * ==========================>
     * 对应字节码：
     *       // access flags 0x2
     *       private testAsm()V
     *       L0
     *       LINENUMBER 7 L0
     *       GETSTATIC java/lang/System.out : Ljava/io/PrintStream;
     *       LDC "Hello Asm"
     *       INVOKEVIRTUAL java/io/PrintStream.println (Ljava/lang/String;)V
     *       L1
     *       LINENUMBER 8 L1
     *       RETURN
     *       L2
     *       LOCALVARIABLE this LTarget2; L0 L2 0
     *       MAXSTACK = 2
     *       MAXLOCALS = 1
     */
    override fun onMethodEnter() {
        super.onMethodEnter()
        // 此处为方法开头
        if (isInjectHello) {
            println("开始插入代码: [ System.out.println(\"Hello Asm\"); ]")
            // 对应->GETSTATIC
            getStatic(Type.getType("Ljava/lang/System;"), "out", Type.getType("Ljava/io/PrintStream;"))
            // 对应->LDC
            visitLdcInsn("Hello Asm")
            // 对应->INVOKEVIRTUAL
            invokeVirtual(Type.getType("Ljava/io/PrintStream;"), Method("println", "(Ljava/lang/String;)V"))
        }
    }

    override fun onMethodExit(opcode: Int) {
        super.onMethodExit(opcode)
        // 此处为方法结尾，适用于类似统计方法执行时长的场景
    }
}