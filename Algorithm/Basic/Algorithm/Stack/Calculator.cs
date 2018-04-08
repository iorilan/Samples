using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Linq.Expressions;

namespace Algorithm.Stack
{
    public class Calculator
    {
        private const string StartScope = "(";
        private const string EndScrope = ")";
        private const int MinPrior = -1;
        private const int NotSupportedOperator = -3;
        private const int FatalError = -4;


        public double Execute(string[] strArr)
        {
            var rpnArr = ConvertToRpn(strArr);

            var expression = new Stack<string>();

            foreach (var s in rpnArr)
            {
                if (IsNum(s))
                {
                    expression.Push(s);
                }
                else if (IsSupportedOperator(s))
                {
                    if (expression.Count < 2) return FatalError;

                    string secondOp = expression.Pop();
                    string firstOp = expression.Pop();

                    double ret = GetResult(firstOp, secondOp, s);
                    expression.Push(ret.ToString());
                }
            }
            return double.Parse(expression.Pop());
        }

        /// <summary>
        /// convert one expression to Reverse Polish notation
        /// </summary>
        /// <param name="strArr"></param>
        /// <returns></returns>
        public string[] ConvertToRpn(string[] strArr)
        {
            var operators = new Stack<string>();
            var expression = new Queue<string>();
            foreach (var str in strArr)
            {
                if (IsNum(str))
                    expression.Enqueue(str);
                else switch (str)
                    {
                        case StartScope:
                            operators.Push(str);
                            break;
                        case EndScrope:
                            {
                                if (operators.Count == 0)
                                    return null;

                                while (operators.Count > 0 && operators.Peek() != StartScope)
                                {
                                    expression.Enqueue(operators.Peek());
                                    operators.Pop();
                                }
                                operators.Pop();
                            }
                            break;
                        default:
                            if (IsSupportedOperator(str))
                            {
                                var stackPrior = operators.Count == 0 ? MinPrior : OperatorPriority(operators.Peek());
                                var elementPrior = OperatorPriority(str);

                                if (elementPrior <= stackPrior)
                                {
                                    while (operators.Count > 0 && OperatorPriority(operators.Peek()) >=
                                                                  elementPrior && operators.Peek() != StartScope)
                                    {
                                        expression.Enqueue(operators.Peek());
                                        operators.Pop();
                                    }
                                }
                                operators.Push(str);
                            }
                            break;
                    }

            }
            while (operators.Count > 0)
            {
                expression.Enqueue(operators.Peek());
                operators.Pop();
            }
            return expression.ToArray();
        }

        private double GetResult(string firstOp, string secondOp, string op)
        {
            if (!IsSupportedOperator(op))
                return NotSupportedOperator;

            var expBlk = new List<Expression>();

            var retExp = Expression.Variable(typeof(double));
            expBlk.Add(retExp);
            var assignExp = Expression.Assign(retExp, Expression.Constant(double.Parse(firstOp), typeof(double)));
            expBlk.Add(assignExp);


            switch (op)
            {
                case "+":
                    expBlk.Add(Expression.AddAssign(retExp, Expression.Constant(double.Parse(secondOp), typeof(double))));
                    break;
                case "-":
                    expBlk.Add(Expression.SubtractAssign(retExp, Expression.Constant(double.Parse(secondOp), typeof(double))));
                    break;
                case "*":
                    expBlk.Add(Expression.MultiplyAssign(retExp, Expression.Constant(double.Parse(secondOp), typeof(double))));
                    break;
                case "/":
                    expBlk.Add(Expression.DivideAssign(retExp, Expression.Constant(double.Parse(secondOp), typeof(double))));
                    break;
            }

            var lmbdExp = Expression.Lambda<Func<double>>(Expression.Block(new[] { retExp }, expBlk));
            var getRet = lmbdExp.Compile();
            return getRet();
        }

        private bool IsNum(string str)
        {
            double rlt;
            return double.TryParse(str, out rlt);
        }

        private int OperatorPriority(string op)
        {
            switch (op)
            {
                case "+":
                case "-":
                    return 1;
                case "*":
                case "/":
                    return 2;
                case StartScope:
                case EndScrope:
                    return 3;
                default:
                    return MinPrior;
            }
        }

        private bool IsSupportedOperator(string str)
        {
            var supportedOps = new[] { "+", "-", "*", "/" };
            return supportedOps.Contains(str);
        }
    }

}
