// MfcCtlTestDlg.h : header file
//

#include "Dialog1.h"
#include "Dialog2.h"
#pragma once#include "afxcmn.h"


// CMfcCtlTestDlg dialog
class CMfcCtlTestDlg : public CDialog
{
// Construction
public:
	CMfcCtlTestDlg(CWnd* pParent = NULL);	// standard constructor
	void Init();
// Dialog Data
	enum { IDD = IDD_MFCCTLTEST_DIALOG };

//members
	CStatic* m_label;
	CListBox* m_lstBox;
	CComboBox* m_comBox;
	CEdit*	  m_txtUserName;
	CTabCtrl m_tabPnl;
	CDialog1 m_dlg1;
	CDialog2 m_dlg2;
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
public:
	afx_msg void OnCbnSelchangeCombotest();
	afx_msg void OnLbnSelchangeLsttest();
	afx_msg void OnBnClickedBtnadd();
	afx_msg void OnBnClickedBtnaddpnl();
	afx_msg void OnTcnSelchangeTabpnl(NMHDR *pNMHDR, LRESULT *pResult);
	
};
