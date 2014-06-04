using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace visimob_desktop
{
    public static class FormataString
    {
        public static string Formata(string s)
        {
            var t = new CultureInfo("pt-BR", false).TextInfo;
            s = t.ToTitleCase(s.ToLower()); //prints "Hello"
            return s;
        }
    }
}