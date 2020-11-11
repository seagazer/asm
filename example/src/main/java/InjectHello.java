import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: Seagazer
 * Date: 2020/11/11
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
@interface InjectHello {
}
