// MfcDrawingTestDlg.cpp : implementation file
//

#include "stdafx.h"
#include "MfcDrawingTest.h"
#include "MfcDrawingTestDlg.h"
#include "math.h"
#ifdef _DEBUG
#define new DEBUG_NEW
#endif


// CMfcDrawingTestDlg dialog




CMfcDrawingTestDlg::CMfcDrawingTestDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CMfcDrawingTestDlg::IDD, pParent)
{
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
}

void CMfcDrawingTestDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
}

BEGIN_MESSAGE_MAP(CMfcDrawingTestDlg, CDialog)
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	//}}AFX_MSG_MAP
	ON_BN_CLICKED(IDOK, &CMfcDrawingTestDlg::OnBnClickedOk)
	ON_BN_CLICKED(IDCANCEL, &CMfcDrawingTestDlg::OnBnClickedCancel)
END_MESSAGE_MAP()


// CMfcDrawingTestDlg message handlers

BOOL CMfcDrawingTestDlg::OnInitDialog()
{
	CDialog::OnInitDialog();

	// Set the icon for this dialog.  The framework does this automatically
	//  when the application's main window is not a dialog
	SetIcon(m_hIcon, TRUE);			// Set big icon
	SetIcon(m_hIcon, FALSE);		// Set small icon

	// TODO: Add extra initialization here

	return TRUE;  // return TRUE  unless you set the focus to a control
}

// If you add a minimize button to your dialog, you will need the code below
//  to draw the icon.  For MFC applications using the document/view model,
//  this is automatically done for you by the framework.

void CMfcDrawingTestDlg::OnPaint()
{
	if (IsIconic())
	{
		CPaintDC dc(this); // device context for painting

		SendMessage(WM_ICONERASEBKGND, reinterpret_cast<WPARAM>(dc.GetSafeHdc()), 0);

		// Center icon in client rectangle
		int cxIcon = GetSystemMetrics(SM_CXICON);
		int cyIcon = GetSystemMetrics(SM_CYICON);
		CRect rect;
		GetClientRect(&rect);
		int x = (rect.Width() - cxIcon + 1) / 2;
		int y = (rect.Height() - cyIcon + 1) / 2;

		// Draw the icon
		dc.DrawIcon(x, y, m_hIcon);
	}
	else
	{
		CDialog::OnPaint();
	}
}

// The system calls this function to obtain the cursor to display while the user drags
//  the minimized window.
HCURSOR CMfcDrawingTestDlg::OnQueryDragIcon()
{
	return static_cast<HCURSOR>(m_hIcon);
}


void CMfcDrawingTestDlg::OnBnClickedOk()
{

double x,y,x0,y0;
double pi = 3.14159254;
CString sa;
CString sx;
CString so;
double da;
double dx;
double doo;

 CEdit* ceA = (CEdit*)GetDlgItem(IDC_TXTA);
 CEdit* ceX = (CEdit*)GetDlgItem(IDC_TXTX);
 CEdit* ceO = (CEdit*)GetDlgItem(IDC_TXTO);
   
   
   
   ceA->GetWindowTextW(sa);
   ceX->GetWindowTextW(sx);
   ceO->GetWindowTextW(so);
   
	da = _wtof(sa.GetBuffer());
	dx = _wtof(sx.GetBuffer());
	doo = _wtof(so.GetBuffer());

 CEdit* ce = (CEdit*)GetDlgItem(IDC_EDIT1);

CClientDC dc(ce);
CPen* penLine = new CPen(PS_SOLID,1,1);
CPen* penPoint = new CPen(PS_SOLID,1,166);
CPen* poldPen = dc.SelectObject(penPoint);
////×ø±êÖá
dc.MoveTo(0,100);
dc.LineTo(300,100);
dc.MoveTo(0,0);
dc.LineTo(0,100);
dc.TextOutW(50,50,_T("aaaa"));
CPen* p = dc.SelectObject(penLine);

for(x = 0;x < 360;x++){
	x0 = x-1;
	//y0 = -x0 + dx;////da£¬Õñ·ù£¬doo£¬ XÆ«ÒÆÁ¿  dx£¬ yÆ«ÒÆÁ¿
	y = -da*sin(x/doo) + dx;
dc.MoveTo(x,y);
dc.LineTo(x0,y);
}
	// TODO: Add your control notification handler code here
	//OnOK();
}

void CMfcDrawingTestDlg::OnBnClickedCancel()
{
Invalidate();////Çå³ý
	// TODO: Add your control notification handler code here
	//OnCancel();
}
