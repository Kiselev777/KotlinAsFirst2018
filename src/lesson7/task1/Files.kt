@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import java.io.File
import java.io.IOException

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                } else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val mapOfString = mutableMapOf<String, Int>()
    for (i in 0 until substrings.size)
        mapOfString[substrings[i]] = Regex(substrings[i].toLowerCase())
                .findAll(File(inputName).readLines().joinToString(separator = " ").toLowerCase()).count()
    return mapOfString
}


/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    val linesInInpute = File(inputName).readLines()
    val mapOfMistake = mapOf('Ы' to 'И', 'ы' to 'и', 'Ю' to 'У',
            'ю' to 'у', 'Я' to 'А', 'я' to 'а')
    (0 until linesInInpute.size).forEach { i ->
        if (linesInInpute[i].length <= 1) {
            outputStream.write(linesInInpute[i])
        } else {
            val char = linesInInpute[i][0].toString()
            outputStream.write(char)
            (1 until linesInInpute[i].length).forEach { j ->
                if ((linesInInpute[i][j] in mapOfMistake.keys) && (linesInInpute[i][j - 1] in "жЖчЧшШщЩ")) {
                    outputStream.write(mapOfMistake[linesInInpute[i][j]].toString())
                } else outputStream.write(linesInInpute[i][j].toString())
            }
        }
        outputStream.newLine()
    }
    outputStream.close()
}

fun Leniza(inputName: String, query: String): String {
    var result = StringBuilder()
    val inputStream = File(inputName).readLines()
    inputStream.forEach { lines ->
        val spl = lines.split(":")
        val splQuery = query.split(" ")
        if (splQuery[0].trim().length != 6)
            throw IllegalArgumentException()
        if (splQuery[0].trim() == spl[0]) {
            val resurs = spl[1].split(",")
            val name = resurs[0].trim()
            val equals = (resurs[1].trim().toInt()) * (resurs[2].trim().toInt())
            if (splQuery[1].trim().toInt() <= resurs[2].trim().toInt())
                result.append("$name,достаточно,общая стоимость $equals р")
            else result.append("$name,недостаточно,общая стоимость $equals р")
        }
        if (!Regex("""\d+\s\d+""").matches(query) ||
                Regex("""\d+:\s+[а-яА-Я]+,\s+\d+\s+[р],\s+\d+\s+\w+""").matches(lines) || result.isEmpty())
            throw IllegalArgumentException()
    }
    if (inputName.isEmpty())
        throw IOException()
    return result.toString()
}

fun football(inputName: String): Map<String, Int> {
    val inputStream = File(inputName).readLines()
    val map = mutableMapOf<String, Int>()
    for (line in inputStream) {
        val splLine = line.split("--")
        val teamName = splLine[0].trim()
        val teamAndResult = splLine[1].trim().split(" ")
        val score = teamAndResult[1].split("-")
        var result = 0
        when {
            score[0].toInt() > score[1].toInt() -> result = 3
            else -> if (score[0].toInt() == score[1].toInt())
                result = 1
        }
        map[teamName] = result
    }
    return map


}


fun moths(inpetName: String, days: String): Int {
    val months = mapOf("январь" to Pair(1, 31), "февраль" to Pair(2, 29), "март" to Pair(3, 31), "апрель" to Pair(4, 30),
            "май" to Pair(5, 31), "июнь" to Pair(6, 30), "июль" to Pair(7, 31), "август" to Pair(8, 31),
            "сентябрь" to Pair(9, 30), "октябрь" to Pair(10, 31), "ноябрь" to Pair(11, 30), "декабрь" to Pair(12, 31))
    val inpetStream = File(inpetName).readLines()
    val splDays = days.split(" ")
    val month = splDays[0]
    val splDaysSecond = splDays[1].split("..")
    val first = splDaysSecond[0].toInt()
    val second = splDaysSecond[1].toInt()
    var max = -1
    inpetStream.forEach { line ->
        val splLine = line.split(" ")
        if (month == splLine[0]) {
            for (i in first until second) {
                // здесь мог напутать с индексами,попробуй если не будет проходить +1
                if (splLine[i].toInt() > max)
                    max = splLine[i].toInt()
            }

        }
    }
    return max
}
val months = mapOf("январь" to Pair(1, 31), "февраль" to Pair(2, 29), "март" to Pair(3, 31), "апрель" to Pair(4, 30),
        "май" to Pair(5, 31), "июнь" to Pair(6, 30), "июль" to Pair(7, 31), "август" to Pair(8, 31),
        "сентябрь" to Pair(9, 30), "октябрь" to Pair(10, 31), "ноябрь" to Pair(11, 30), "декабрь" to Pair(12, 31))


fun myfun(inputName: String, days: String): Any {
    val str = try { File(inputName).readLines() } catch (e: IOException)
    { throw IOException("Невозможно прочитать файл $inputName !") }

    if (!Regex("""[а-яА-Я]+\s\d{1,2}.{3}(\d{1,2}|([а-яА-Я]+\s\d{1,2}))""").matches(days))
        throw IllegalArgumentException("Параметр days задан в неверном формате!")

    val tempList = mutableListOf<Int>()
    str.forEach {
        if (!Regex("""[а-яА-Я]+(\s\d+)+""").matches(it))
            throw IllegalArgumentException("Файл $inputName задан в неверном формате!")
        if (!months.keys.contains(it.split(" ").first().toLowerCase()) ||
                it.split(" ").size != months[it.split(" ").first().toLowerCase()]!!.second &&
                it.split(" ").first().toLowerCase() != "февраль")
            throw IllegalArgumentException("Файл $inputName задан в неверном формате!")
    }

    var temp = months[str.first().split(" ").first().toLowerCase()]!!.first
    str.forEach {
        val month = it.split(" ").first().toLowerCase()
        if ((months[month]!!.first - temp != 1) || (months[month]!!.first == 1 && months[month]!!.first - temp != -30))
            throw IllegalArgumentException("Месяца в списке заданы не по порядку!")
        temp = months[month]!!.first
    }

    return 0
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    val linesInInput = File(inputName).readLines()
    val outputStream = File(outputName).bufferedWriter()
    val max = maxLineLength(inputName)
    linesInInput.forEach { line ->
        when {
            line.trim().length < max -> outputStream.write(" ".repeat((max - line.trim().length) / 2) + line.trim())
            else -> outputStream.write(line.trim())
        }
        outputStream.newLine()
    }
    outputStream.close()
}

fun maxLineLength(inputName: String): Int {
    val linesInInput = File(inputName).readLines()
    var max = 0
    linesInInput.forEach { line ->
        when {
            line.trim().length > max -> max = line.trim().length
        }
    }
    return max
}

/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    val linesInInput = File(inputName).readLines()
    val inputStream = File(outputName).bufferedWriter()
    val max = maxLineLength(inputName)
    linesInInput.forEach { line ->
        val correct: String
        val word = line.split(" ").filter { (it.isNotEmpty()) }.toMutableList()
        var wordLength = word.joinToString("").length
        when {
            word.size <= 1 -> correct = line.trim()
            else -> {
                while (max > wordLength)
                    for (i in 0 until word.size - 1) {
                        when {
                            max > wordLength -> {
                                word[i] += " "
                                wordLength++
                            }
                        }
                    }
                correct = word.joinToString("")
            }
        }
        inputStream.write(correct)
        inputStream.newLine()
    }
    inputStream.close()
}


/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> {
    TODO()
}

/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    val wordInInput = File(inputName).readLines()
    var max = -1
    val result = mutableSetOf<String>()
    wordInInput.forEach { word ->
        if (word.toLowerCase().toSet().size == word.length) {
            if (word.length >= max)
                max = word.length

        }
    }
    wordInInput.forEach { word ->
        if (word.toLowerCase().toSet().size == max && word.toLowerCase().toSet().size == word.length)
            result.add(word)
    }
    outputStream.write(result.joinToString(", "))
    outputStream.close()
}


/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>
Или колбаса
</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>
Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ul>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    TODO()
}

/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    TODO()
}


/**
 * Сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    TODO()
}

