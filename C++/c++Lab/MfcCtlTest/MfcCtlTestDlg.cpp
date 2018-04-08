// MfcCtlTestDlg.cpp : implementation file
//

#include "stdafx.h"
#include "MfcCtlTest.h"
#include "MfcCtlTestDlg.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


// CMfcCtlTestDlg dialog




CMfcCtlTestDlg::CMfcCtlTestDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CMfcCtlTestDlg::IDD, pParent)
{
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
}

void CMfcCtlTestDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	DDX_Control(pDX, IDC_TABPNL, m_tabPnl);
}

BEGIN_MESSAGE_MAP(CMfcCtlTestDlg, CDialog)
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	//}}AFX_MSG_MAP
	ON_CBN_SELCHANGE(IDC_COMBOTEST, &CMfcCtlTestDlg::OnCbnSelchangeCombotest)
	ON_LBN_SELCHANGE(IDC_LSTTEST, &CMfcCtlTestDlg::OnLbnSelchangeLsttest)
	ON_BN_CLICKED(IDC_BTNADD, &CMfcCtlTestDlg::OnBnClickedBtnadd)
	ON_BN_CLICKED(IDC_BTNADDPNL, &CMfcCtlTestDlg::OnBnClickedBtnaddpnl)
	ON_NOTIFY(TCN_SELCHANGE, IDC_TABPNL, &CMfcCtlTestDlg::OnTcnSelchangeTabpnl)
END_MESSAGE_MAP()


// CMfcCtlTestDlg message handlers

BOOL CMfcCtlTestDlg::OnInitDialog()
{
	CDialog::OnInitDialog();

	// Set the icon for this dialog.  The framework does this automatically
	//  when the application's main window is not a dialog
	SetIcon(m_hIcon, TRUE);			// Set big icon
	SetIcon(m_hIcon, FALSE);		// Set small icon

	// TODO: Add extra initialization here
	this->Init();



	return TRUE;  // return TRUE  unless you set the focus to a control
}

// If you add a minimize button to your dialog, you will need the code below
//  to draw the icon.  For MFC applications using the document/view model,
//  this is automatically done for you by the framework.

void CMfcCtlTestDlg::OnPaint()
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
HCURSOR CMfcCtlTestDlg::OnQueryDragIcon()
{
	return static_cast<HCURSOR>(m_hIcon);
}



void CMfcCtlTestDlg::Init(){
	////初始化控件句柄
	this->m_comBox = (CComboBox*)GetDlgItem(IDC_COMBOTEST);//获得当前combobox句柄
	this->m_lstBox = (CListBox*)GetDlgItem(IDC_LSTTEST);
	this->m_label = (CStatic*)GetDlgItem(IDC_SHOWTEXT);
	this->m_txtUserName = (CEdit*)GetDlgItem(IDC_TXTUSRNAME);
	//m_pnlTab = (CTabCtrl*)GetDlgItem(IDC_TABPNL);

CStringList list ;
list.AddTail(_T("aaa"));
list.AddTail(_T("bbb"));
list.AddTail(_T("ccc"));
list.AddTail(_T("ddd"));
list.AddTail(_T("eee"));
list.AddTail(_T("fff"));

POSITION rPos;
rPos = list.GetHeadPosition();
while (rPos != NULL)
{
   CString s= list.GetNext(rPos);
   this->m_comBox->AddString(s);
}


//////////////////////////////


POSITION rPos1;
rPos1 = list.GetHeadPosition();
while (rPos1 != NULL)
{
   CString s= list.GetNext(rPos1);
   this->m_lstBox->AddString(s);
}

//free(list);

///////////////TAB ///////////////
//TCITEM item;
//item.mask = TCIF_TEXT;
//item.pszText = _T("第一页");

m_tabPnl.InsertItem(0,_T("第一页"));
m_tabPnl.InsertItem (1,_T("第二页"));


m_dlg1.Create(IDD_DIALOG1,&m_tabPnl);
m_dlg2.Create(IDD_DIALOG2,&m_tabPnl);
 
m_dlg1.SetWindowPos(NULL,20,20,400,200,SWP_SHOWWINDOW);
m_dlg2.SetWindowPos(NULL,20,20,400,200,SWP_HIDEWINDOW);

}



void CMfcCtlTestDlg::OnCbnSelchangeCombotest()
{
//this->m_comBox = (CComboBox*)GetDlgItem(IDC_COMBOTEST);//获得当前combobox句柄
//	this->m_lstBox = (CListBox*)GetDlgItem(IDC_LSTTEST);

 CString strCom;
 this->m_comBox->GetLBText(this->m_comBox->GetCurSel(),strCom);

	int i;
	for(i = 0;i < this->m_lstBox->GetCount();i ++){
 CString strLst;
 this->m_lstBox->GetText(i,strLst);

 if(strLst.Compare(strCom) == 0){
	 this->m_lstBox->SetCurSel(i);
////设置标签内容   
this->m_label->SetWindowTextW(strCom);
 }
	}


	// TODO: Add your control notification handler code here
}

void CMfcCtlTestDlg::OnLbnSelchangeLsttest()
{

 CString strLst;
 this->m_lstBox->GetText( this->m_lstBox->GetCurSel(),strLst);

	int i;
	for(i = 0;i <  this->m_comBox->GetCount();i ++){
 CString strCom;
 this->m_comBox->GetLBText(i,strCom);

 if(strLst.Compare(strCom) == 0){
	  this->m_comBox->SetCurSel(i);
////设置标签内容   
this->m_label->SetWindowTextW(strLst);
 }
	}
	// TODO: Add your control notification handler code here
}

void CMfcCtlTestDlg::OnBnClickedBtnadd()
{
	CString str;
  this->m_txtUserName->GetWindowTextW(str);
	this->m_lstBox->AddString(str);
	this->m_comBox->AddString(str);
	// TODO: Add your control notification handler code here
}

void CMfcCtlTestDlg::OnBnClickedBtnaddpnl()
{

	// TODO: Add your control notification handler code here
}

void CMfcCtlTestDlg::OnTcnSelchangeTabpnl(NMHDR *pNMHDR, LRESULT *pResult)
{
	switch(m_tabPnl.GetCurSel())
{
case 0:
m_dlg1.ShowWindow(true);
m_dlg2.ShowWindow(false);
break;
case 1:
m_dlg1.ShowWindow(false);
m_dlg2.ShowWindow(true);
break;
}
*pResult = 0;
	// TODO: Add your control notification handler code here
	*pResult = 0;
}
