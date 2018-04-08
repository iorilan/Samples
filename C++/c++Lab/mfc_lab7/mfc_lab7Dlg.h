// mfc_lab7Dlg.h : header file
//

#pragma once


// Cmfc_lab7Dlg dialog
class Cmfc_lab7Dlg : public CDialog
{
// Construction
public:
	Cmfc_lab7Dlg(CWnd* pParent = NULL);	// standard constructor
	void Cmfc_lab7Dlg::OnMenuSelect(UINT ID,UINT flag,UINT sysMenu);
void Cmfc_lab7Dlg::OnInitMenuPopup(CMenu *pPopupMenu, UINT nIndex, BOOL bSysMenu);
// Dialog Data
	enum { IDD = IDD_MFC_LAB7_DIALOG };

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
};
