using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Algorithm
{
    class FormatCode
    {
        public static string Format(StringBuilder code)
        {
            var lines = code.ToString().Split('\n');
            if (lines.Length < 1) return code.ToString();

            var lstStr = new List<string>();
            var level = 0;
            foreach (var t in lines)
            {
                var strPrefix = "";
                switch (t)
                {
                    case ("{\r"):
                        level++;
                        break;
                    case ("}\r"):
                        level--;
                        break;
                }

                for (var j = 0; j < level; j++)
                {
                    strPrefix += "\t";
                }
                if (level > 0)
                {
                    if (t == "{\r")
                        lstStr.Add(strPrefix.Remove(0, 1) + t);
                    else
                        lstStr.Add(strPrefix + t);
                }
                else
                {
                    lstStr.Add(t);
                }
            }

            const string retStr = "";
            return lstStr.Aggregate(retStr, (current, t) => current + t);

        }
    }
}
