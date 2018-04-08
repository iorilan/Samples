// DrawDlg.cpp : implementation file
//

#include "stdafx.h"
#include "mfcLab4.h"
#include "DrawDlg.h"


// DrawDlg dialog

IMPLEMENT_DYNAMIC(DrawDlg, CDialog)

DrawDlg::DrawDlg(CWnd* pParent /*=NULL*/)
	: CDialog(DrawDlg::IDD, pParent)
{

}

DrawDlg::~DrawDlg()
{
}

void DrawDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
}


BEGIN_MESSAGE_MAP(DrawDlg, CDialog)
	ON_BN_CLICKED(IDOK, &DrawDlg::OnBnClickedOk)
	ON_WM_LBUTTONDOWN()
END_MESSAGE_MAP()


// DrawDlg message handlers

void DrawDlg::OnBnClickedOk()
{

	// TODO: Add your control notification handler code here
	//OnOK();
}

void DrawDlg::OnLButtonDown(UINT nFlags, CPoint point)
{
	// TODO: Add your message handler code here and/or call default
AfxMessageBox(_T("left"));
	CDialog::OnLButtonDown(nFlags, point);
}
