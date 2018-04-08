using System;
using SQLite;

namespace LocalStorage
{
    public abstract class LocalStorageBaseModel
    {
        [PrimaryKey]
        public Guid Id { get; set; }

        protected LocalStorageBaseModel()
        {
            Id = Guid.NewGuid();
        }
    }
}
