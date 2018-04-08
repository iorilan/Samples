// lab_mfc8Dlg.h : header file
//

#pragma once


// Clab_mfc8Dlg dialog
class Clab_mfc8Dlg : public CDialog
{
// Construction
public:
	Clab_mfc8Dlg(CWnd* pParent = NULL);	// standard constructor
void Clab_mfc8Dlg::OnInitMenuPopup(CMenu *pPopupMenu, UINT nIndex, BOOL bSysMenu);
// Dialog Data
	enum { IDD = IDD_LAB_MFC8_DIALOG };

	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support


// Implementation
protected:
	HICON m_hIcon;

	// Generated message map functions
	virtual BOOL OnInitDialog();
	afx_msg void OnSysCommand(UINT nID, LPARAM lParam);
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	afx_msg void OnMenuSelect(UINT nItemID, UINT nFlags, HMENU hSysMenu);
	DECLARE_MESSAGE_MAP()
};
