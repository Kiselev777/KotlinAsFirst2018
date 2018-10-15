@file:Suppress("UNUSED_PARAMETER")

package lesson2.task2

import lesson1.task1.sqr
import java.lang.Math.sqrt
import kotlin.math.sqrt

/**
 * Пример
 *
 * Лежит ли точка (x, y) внутри окружности с центром в (x0, y0) и радиусом r?
 */
fun pointInsideCircle(x: Double, y: Double, x0: Double, y0: Double, r: Double) =
        sqr(x - x0) + sqr(y - y0) <= sqr(r)

/**
 * Простая
 *
 * Четырехзначное число назовем счастливым, если сумма первых двух ее цифр равна сумме двух последних.
 * Определить, счастливое ли заданное число, вернуть true, если это так.
 */
fun isNumberHappy(number: Int): Boolean {
    if (number in 1000..9999) {
        if (number / 1000 + (number / 100) % 10 == (number / 10) % 10 + number % 10)
            return true
    }
    return false
}


/**
 * Простая
 *
 * На шахматной доске стоят два ферзя (ферзь бьет по вертикали, горизонтали и диагоналям).
 * Определить, угрожают ли они друг другу. Вернуть true, если угрожают.
 * Считать, что ферзи не могут загораживать друг друга.
 */
fun queenThreatens(x1: Int, y1: Int, x2: Int, y2: Int): Boolean {
    if (x1 == x2 || y1 == y2 || Math.sqrt(sqr(x1).toDouble() + sqr(y1).toDouble()) == Math.sqrt(sqr(x2).toDouble() + sqr(y2))) {
        return true

    }
    return false
}


/**
 * Простая
 *
 * Дан номер месяца (от 1 до 12 включительно) и год (положительный).
 * Вернуть число дней в этом месяце этого года по григорианскому календарю.
 */
fun daysInMonth(month: Int, year: Int): Int{
    return when{
        month in 1..7 step 2 -> 31
        month in 8..12 step 2 -> 31
        month in 4..6  step 2 ->30
        month in 9..11 step 2 -> 30
        else -> when{
            year % 400 == 0 -> 29
            (year % 100 == 0) &&(year % 400 > 0) -> 28
            year % 4 == 0 -> 29
            else -> 28
        }
    }
}

/**
 * Средняя
 *
 * Проверить, лежит ли окружность с центром в (x1, y1) и радиусом r1 целиком внутри
 * окружности с центром в (x2, y2) и радиусом r2.
 * Вернуть true, если утверждение верно
 */
fun circleInside(x1: Double, y1: Double, r1: Double,
                 x2: Double, y2: Double, r2: Double): Boolean {
    return r2 >= Math.sqrt(sqr(x2 - x1) + sqr(y2 - y1)) + r1
}

/**
 * Средняя
 *
 * Определить, пройдет ли кирпич со сторонами а, b, c сквозь прямоугольное отверстие в стене со сторонами r и s.
 * Стороны отверстия должны быть параллельны граням кирпича.
 * Считать, что совпадения длин сторон достаточно для прохождения кирпича, т.е., например,
 * кирпич 4 х 4 х 4 пройдёт через отверстие 4 х 4.
 * Вернуть true, если кирпич пройдёт
 */
fun brickPasses(a: Int, b: Int, c: Int, r: Int, s: Int): Boolean {
    var maxLength = maxOf(a, b, c)
    var minLength = minOf(a, b, c)
    var medLength = (a + b + c) - minLength - maxLength
    return if (minLength <= r && minLength <= s)
        if (medLength <= s || medLength <= r)
            true
        else
            false
    else
        false


}
