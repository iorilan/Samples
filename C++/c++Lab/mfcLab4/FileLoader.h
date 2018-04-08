#pragma once


class FileLoader
{
public:

	FileLoader(void);
	bool ContainFile(CString path);
    void LoadFiles(CString path,int deep);
	~FileLoader(void);

	CStringList* files;
	CStringList* dirs;

};
