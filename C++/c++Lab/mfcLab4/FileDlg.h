#pragma once



class FileDlg : public CDialog
{
	DECLARE_DYNAMIC(FileDlg)

	typedef void(Callback)(CString); 

public:
	FileDlg(CWnd* pParent = NULL);   // standard constructor
	void FindFiles(CTreeCtrl* m_Tree,HTREEITEM root,HTREEITEM parent,CString strPath,int deep);
	void OpenFileDialog();
	void OpenDirDialog();
	
	virtual ~FileDlg();

// Dialog Data
	enum { IDD = IDD_FILEDLG };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support

	DECLARE_MESSAGE_MAP()
public:
	afx_msg void OnBnClickedOpenfile();
	afx_msg void OnBnClickedSelfiles();
};
