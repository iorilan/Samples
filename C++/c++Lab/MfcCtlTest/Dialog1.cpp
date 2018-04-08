// Dialog1.cpp : implementation file
//

#include "stdafx.h"
#include "MfcCtlTest.h"
#include "Dialog1.h"


// CDialog1 dialog

IMPLEMENT_DYNAMIC(CDialog1, CDialog)

CDialog1::CDialog1(CWnd* pParent /*=NULL*/)
	: CDialog(CDialog1::IDD, pParent)
{

}

CDialog1::~CDialog1()
{
}

void CDialog1::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
}


void CDialog1::OnVScroll(UINT nSBCode, UINT nPos, CScrollBar* pScrollBar)
{
	// TODO: Add your message handler code here and/or call default

	CDialog::OnVScroll(nSBCode, nPos, pScrollBar);
}

BEGIN_MESSAGE_MAP(CDialog1, CDialog)
	ON_WM_VSCROLL()
END_MESSAGE_MAP()


// CDialog1 message handlers
