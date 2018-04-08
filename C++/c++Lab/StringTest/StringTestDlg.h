// StringTestDlg.h : header file
//

#pragma once


// CStringTestDlg dialog
class CStringTestDlg : public CDialog
{
// Construction
public:
	CStringTestDlg(CWnd* pParent = NULL);	// standard constructor

	CString RemoveExtraSpace(CString str);
	CString FindStr(CStringArray* strArr,CString strToFind);
// Dialog Data
	enum { IDD = IDD_STRINGTEST_DIALOG };

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
	afx_msg void OnBnClickedOk();
	afx_msg void OnBnClickedCancel();
	afx_msg void OnBnClickedBtnshowpic();
};
