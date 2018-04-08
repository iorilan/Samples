using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;

namespace Algorithm
{
    public class DirFileSearch
    {
        private string _pattern;
        private SearchOption _searchOption;

        public void SetPattern(string newPattern)
        {
            _pattern = newPattern;
        }

        public void SetOption(SearchOption newOption)
        {
            _searchOption = newOption;
        }

        public DirFileSearch(string pattern, SearchOption searchOption)
        {
            _pattern = pattern;
            _searchOption = searchOption;
        }

        public void GetFiles(string baseDir, out List<FileInfo> files)
        {
            var dir = new DirectoryInfo(baseDir);
            files = dir.GetFiles(_pattern, _searchOption).ToList();
        }

        public void GetDirs(string baseDir, out List<DirectoryInfo> dirs)
        {
            var dir = new DirectoryInfo(baseDir);
            dirs = dir.GetDirectories(_pattern, _searchOption).ToList();
        }

        public void GetDirFiles(DirInfo curDir,string pattern)
        {
            var currDir = new DirectoryInfo(curDir.FullName);
            if (!currDir.Exists) return;

            curDir.Files = curDir.Files ?? new List<FileInfo>();
            curDir.SubDirs = curDir.SubDirs ?? new List<DirInfo>();
            
            var currFiles = currDir.GetFiles(pattern, SearchOption.TopDirectoryOnly);
            if (currFiles.Any())
            {   
                foreach (var file in currFiles)
                {
                    curDir.Files.Add(file);
                }
            }

            // sub directory
            var subDirs = currDir.GetDirectories(pattern, SearchOption.TopDirectoryOnly);

            if (subDirs.Any())
            {

                foreach (var directoryInfo in subDirs)
                {
                    var newSub = new DirInfo() {FullName = directoryInfo.FullName};
                    GetDirFiles(newSub,pattern);
                    curDir.SubDirs.Add(newSub);
                }
                
            }
        }
    }

    public class DirInfo
    {
        public List<FileInfo> Files { get; set; }
        public List<DirInfo> SubDirs { get; set; } 
        public string FullName { get; set; }

    }
}
