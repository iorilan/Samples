// StringTestDlg.cpp : implementation file
//

#include "stdafx.h"
#include "StringTest.h"
#include "StringTestDlg.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


// CStringTestDlg dialog




CStringTestDlg::CStringTestDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CStringTestDlg::IDD, pParent)
{
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
}

void CStringTestDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
}

BEGIN_MESSAGE_MAP(CStringTestDlg, CDialog)
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	//}}AFX_MSG_MAP
	ON_BN_CLICKED(IDOK, &CStringTestDlg::OnBnClickedOk)
	ON_BN_CLICKED(IDCANCEL, &CStringTestDlg::OnBnClickedCancel)
	ON_BN_CLICKED(IDC_BTNSHOWPIC, &CStringTestDlg::OnBnClickedBtnshowpic)
END_MESSAGE_MAP()


// CStringTestDlg message handlers

BOOL CStringTestDlg::OnInitDialog()
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

void CStringTestDlg::OnPaint()
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
HCURSOR CStringTestDlg::OnQueryDragIcon()
{
	return static_cast<HCURSOR>(m_hIcon);
}



////消除多余空格
void CStringTestDlg::OnBnClickedOk()
{
	CString str("i am    a  stu aa aa   ddd  s");
	CString rlt = RemoveExtraSpace(str);
	AfxMessageBox(rlt);
	// TODO: Add your control notification handler code here
	//OnOK();
}

CString CStringTestDlg::RemoveExtraSpace(CString str){
CString ret;
	int i = 0;
	bool lastChrIsSpace = false;
	for(i = 0;i < str.GetLength() ;i ++){
		
		
		if(str[i] == ' '){
			if(lastChrIsSpace){
			continue;
			}
			else{
			ret.AppendChar('_');
			lastChrIsSpace = true;
			}
		}
		else{
			ret.AppendChar(str.GetAt(i));
		lastChrIsSpace = false;
		}
	}
	return ret;
}

///////////////////////////////////////


/////查找字符串///////////////////////

CString CStringTestDlg::FindStr(CStringArray* strArr,CString strToFind){

	int i = 0;
	CString curStr;
  for(i = 0;i < strArr->GetCount();i ++){
  curStr = strArr->GetAt(i);
  if(curStr.Find(strToFind) > -1){
  return curStr;
  }
  }
  return NULL;
  
  
}

void CStringTestDlg::OnBnClickedCancel()
{
	CStringArray arr ;
	arr.Add(_T("Hello aa Da A dd "));
	arr.Add(_T("Mfc aa Da A dd "));
	arr.Add(_T("AAA aa Da A dd "));
	arr.Add(_T("Dff aa Da A dd "));
	CString ret = this->FindStr(&arr,_T("Mfc"));
	if(ret.GetLength() > 0){
		CString rplSpaceTo_ = this->RemoveExtraSpace(ret);
	AfxMessageBox(rplSpaceTo_);
	}
	// TODO: Add your control notification handler code here
	//OnCancel();
}

/////////////////////////////////

/////显示位图

void CStringTestDlg::OnBnClickedBtnshowpic()
{
	CClientDC dc(this);
	dc.TextOutW(100,100,_T("hi mfc"));


CStatic* pStatic=(CStatic*)GetDlgItem(IDC_PIC);
CDC* pDC=pStatic->GetDC();
CBitmap bitmap;
bitmap.LoadBitmap(IDB_BITMAP2);
BITMAP bmp;
bitmap.GetBitmap(&bmp);
CDC dcCompatible;
dcCompatible.CreateCompatibleDC(pDC);

dcCompatible.SelectObject(&bitmap);
CRect rect;
pStatic->GetClientRect(&rect);

pDC->StretchBlt(0,0,rect.Width(),rect.Height(), &dcCompatible,0,0,bmp.bmWidth,bmp.bmHeight,SRCCOPY);
	// TODO: Add your control notification handler code here
}


///////////////////////////////////////////////