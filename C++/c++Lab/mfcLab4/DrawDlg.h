#pragma once


// DrawDlg dialog

class DrawDlg : public CDialog
{
	DECLARE_DYNAMIC(DrawDlg)

public:
	DrawDlg(CWnd* pParent = NULL);   // standard constructor
	virtual ~DrawDlg();

// Dialog Data
	enum { IDD = IDD_DRAWDLG };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support

	DECLARE_MESSAGE_MAP()
public:
	afx_msg void OnBnClickedOk();
	afx_msg void OnLButtonDown(UINT nFlags, CPoint point);
};
