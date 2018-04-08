// FileDlg.cpp : implementation file
//

#include "stdafx.h"
#include "mfcLab4.h"
#include "FileDlg.h"
#include "FileLoader.h"

// FileDlg dialog

IMPLEMENT_DYNAMIC(FileDlg, CDialog)

FileDlg::FileDlg(CWnd* pParent /*=NULL*/)
	: CDialog(FileDlg::IDD, pParent)
{

}

FileDlg::~FileDlg()
{
}

void FileDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
}


BEGIN_MESSAGE_MAP(FileDlg, CDialog)
	ON_BN_CLICKED(IDC_OPENFILE, &FileDlg::OnBnClickedOpenfile)
	ON_BN_CLICKED(IDC_SELFILES, &FileDlg::OnBnClickedSelfiles)
END_MESSAGE_MAP()


// FileDlg message handlers

////递归查找文件
void FileDlg::FindFiles(CTreeCtrl* m_Tree,HTREEITEM root,HTREEITEM parent,CString strPath,int deep){

	if(deep > 10){
		return;
	}
	 CString strWildcard(strPath);
     strWildcard += _T("\\*.*");
   CFileFind ff;
   BOOL bFind = ff.FindFile(strWildcard);

   while(bFind)
   {
      bFind = ff.FindNextFile();
      if (ff.IsDots() || ff.IsSystem() || ff.IsHidden())
         continue;
      
      if (ff.IsDirectory())
      {
		 CString dir = ff.GetFilePath();

		HTREEITEM currentNode;
		 if(parent==NULL){
		currentNode = m_Tree->InsertItem(dir,NULL,NULL,root);
		 }
		 else{
		currentNode = m_Tree->InsertItem(dir,NULL,NULL,parent);
		 }
		 this->FindFiles(m_Tree,root,currentNode,dir,++deep);

      }
      else
      {
         CString strFileName = ff.GetFileName();
		 if(parent==NULL){
		 m_Tree->InsertItem(strFileName,NULL,NULL,root);
		 }
		 else{
		 m_Tree->InsertItem(strFileName,NULL,NULL,parent);
		 }
		 
      }
   }
}

void FileDlg::OnBnClickedOpenfile()
{
	this->OpenFileDialog();
	
	// TODO: Add your control notification handler code here
}

////对话框====================================

////打开文件对话框
void FileDlg::OpenFileDialog(){

CFileDialog dlg(TRUE, NULL, NULL,
           OFN_HIDEREADONLY | OFN_OVERWRITEPROMPT,
           NULL);

    if(dlg.DoModal() == IDOK)
    {
          CString szFilename = dlg.GetPathName();

CFile f;
f.Open(szFilename,CFile::modeReadWrite);
char* buf = new char[f.GetLength()];

f.Read(buf,f.GetLength());
f.Close();

CString s(buf);
GetDlgItem(IDC_EDIT1)->SetWindowTextW(s);
delete[] buf;

    }
}


////打开目录对话框
void FileDlg::OpenDirDialog(){
CString strP =_T("选择目录");  
	int length = 1024;

LPWSTR szPath = strP.GetBuffer(1024);

// do something with the string that pwsz points to.

strP.ReleaseBuffer();

    CString str;
    BROWSEINFO bi;   
    bi.hwndOwner = m_hWnd;   
    bi.pidlRoot = NULL;   
    bi.pszDisplayName = szPath;   
    bi.lpszTitle = _T("请选择要遍历的目录：");   
    bi.ulFlags = 0;   
    bi.lpfn = NULL;   
    bi.lParam = 0;   
    bi.iImage = 0;   
    //弹出选择目录对话框
    LPITEMIDLIST lp = SHBrowseForFolder(&bi);  

    if(lp && SHGetPathFromIDList(lp,szPath))   
    {
		CTreeCtrl* m_Tree = (CTreeCtrl*)GetDlgItem(IDC_FILETREE);
	////设置样式
m_Tree->ModifyStyle(NULL,TVS_HASBUTTONS | TVS_HASLINES | TVS_LINESATROOT); 
HTREEITEM root = m_Tree->InsertItem(szPath,NULL,NULL);///root就是节点的标题
	
this->FindFiles(m_Tree,root,NULL,szPath,0);
    }
	else {
        AfxMessageBox(_T("无效的目录，请重新选择"));  
		
	}
}



//=======================================

void FileDlg::OnBnClickedSelfiles()
{
	this->OpenDirDialog();
	// TODO: Add your control notification handler code here
}
