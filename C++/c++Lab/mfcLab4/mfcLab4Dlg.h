// mfcLab4Dlg.h : header file
//


#pragma once


// CmfcLab4Dlg dialog
class CmfcLab4Dlg : public CDialog
{
// Construction
public:
	
	CmfcLab4Dlg(CWnd* pParent = NULL);	// standard constructor

// Dialog Data
	enum { IDD = IDD_MFCLAB4_DIALOG };
	 void OnInitMenuPopup(CMenu *pPopupMenu, UINT nIndex,BOOL bSysMenu);
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support
	

// Implementation
protected:
	HICON m_hIcon;

	// Generated message map functions
	virtual BOOL OnInitDialog();
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();

	DECLARE_MESSAGE_MAP()
	//DECLARE_MESSAGE_MAP()
		
	 

public:
	afx_msg void OnBnClickedOk();
	afx_msg void OnBnClickedCancel();
	afx_msg void OnEnChangeEdit1();
	afx_msg void OnBnClickedBtnlstsel();
	afx_msg void OnBnClickedBtnnewfrm();
	afx_msg void OnBnClickedBtnshowfile();
	afx_msg void OnBnClickedBtndraw();
//	afx_msg void OnMenuSelect(UINT nItemID, UINT nFlags, HMENU hSysMenu);
	afx_msg void OnBnClickedBtnmenu();
	afx_msg void OnHiMfc();


};
