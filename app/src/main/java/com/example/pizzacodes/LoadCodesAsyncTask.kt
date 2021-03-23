package com.example.pizzacodes

import android.os.AsyncTask
import android.util.Log
import com.example.pizzacodes.database.AppDatabase
import com.example.pizzacodes.database.Code
import java.net.URL
import java.nio.charset.Charset
import java.util.regex.Matcher
import java.util.regex.Pattern


class LoadCodesAsyncTask(val mDatabase: AppDatabase): AsyncTask<String, Unit, List<Code>>() {
    private val TAG: String = "LoadCodesAsyncTask"

    override fun doInBackground(vararg urls: String?): List<Code> {
        val codesUrl: String = urls[0]?:""
        var text: String = URL(codesUrl).readText(Charset.forName("Windows-1252"))

        fun String.replaceUnicode(): String{
            val p = Pattern.compile("\\\\u(\\p{XDigit}{4})")
            val m: Matcher = p.matcher(this)
            val buf = StringBuffer(this.length)
            while (m.find()) {
                val ch: String = (m.group(1).toInt(16).toChar()).toString()
                m.appendReplacement(buf, Matcher.quoteReplacement(ch))
            }
            m.appendTail(buf)
            return buf.toString()
        }
        text = text.replaceUnicode()
        //Log.d(TAG, text)

        val result = ArrayList<Code>()
        mDatabase.codeDao().deleteAllCodes()


        val regexArray = Regex("""\[.+?\]""")
        val arrayText = regexArray.find(text)?.value
        val regexItem = Regex("""\{.+?\}""")
        val regexTogether = Regex(""""name":"(.+?)","code":"(.+?)"""")
        val regexParseCode = Regex(""".+?-.+?-(.+?)-(.+?)-(.+$)""")
        val regexGetCost = Regex(""".+?(\d+)[\.,](\d+).+?""")

        //val regexTemp =

        val matches = regexItem.findAll(arrayText.toString())
        matches.forEach { m ->
            val itemString = m.value.toString()
            val (str, code) = regexTogether.find(itemString)!!.destructured
            val isFood: Boolean = !str.contains('%')
            val (description, costStr, cities) = regexParseCode.find(str)!!.destructured
            Log.d(TAG, str)
            Log.d(TAG, cities)
            val (left, right) = regexGetCost.find(costStr)!!.destructured
            val cost = left.toInt() * 100 + right.toInt()
            val newCode = Code(code, description, cost, cities, isFood)
            mDatabase.codeDao().insertCode(newCode)
            result.add(newCode)
        }
        return result
    }
}