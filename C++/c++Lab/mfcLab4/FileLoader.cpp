
#program once
#include "StdAfx.h"

#include "FileLoader.h"

FileLoader::FileLoader(void)
{
	this->files = new CStringList();
	this->dirs = new CStringList();
}

FileLoader::~FileLoader(void)
{
	free(this->files);
	free(this->dirs);
}

bool FileLoader::ContainFile(CString path){
return true;
}

void FileLoader::LoadFiles(CString strPath,int deep){
	
	
	
}
