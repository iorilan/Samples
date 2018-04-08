using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;

namespace Helpers
{
    public class toreProcedureCalls : IStoreProcedureCalls
    {
        public IContext Context { get; set; }

        /// <summary>
        /// should be only created from TicketingDataContext
        /// </summary>
        /// <param name="context"></param>
        public StoreProcedureCalls(IContext context)
        {
            Context = context;
        }

        public IEnumerable<model> call(string p1, string p2)
        {
            using (var context = Context.NewInstance())
            {
                var result = context.Database.SqlQuery<SP_CableCarUsageHistory>("EXEC sp @p1, @p2",
                    new SqlParameter("p1", p1),
                    new SqlParameter("p2", p2)).ToList();
                return result;
            }
        }
    }
}
