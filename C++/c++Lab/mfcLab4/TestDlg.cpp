// TestDlg.cpp : implementation file
//

#include "stdafx.h"
#include "mfcLab4.h"
#include "TestDlg.h"


// TestDlg dialog

IMPLEMENT_DYNAMIC(TestDlg, CDialog)

TestDlg::TestDlg(CWnd* pParent /*=NULL*/)
	: CDialog(TestDlg::IDD, pParent)
{

}

TestDlg::~TestDlg()
{
}

void TestDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
}


BEGIN_MESSAGE_MAP(TestDlg, CDialog)
	ON_BN_CLICKED(IDOK, &TestDlg::OnBnClickedOk)
	ON_BN_CLICKED(IDC_BUTTON1, &TestDlg::OnBnClickedButton1)
	ON_BN_CLICKED(IDC_BUTTON2, &TestDlg::OnBnClickedButton2)
	ON_BN_CLICKED(IDC_BUTTON3, &TestDlg::OnBnClickedButton3)
	ON_COMMAND(ID_HI_MFC, &TestDlg::OnHiMfc)
END_MESSAGE_MAP()


// TestDlg message handlers

void TestDlg::OnBnClickedOk()
{

	CTreeCtrl* m_Tree = (CTreeCtrl*)GetDlgItem(IDC_TREE1);

	////设置样式
m_Tree->ModifyStyle(NULL,TVS_HASBUTTONS | TVS_HASLINES | TVS_LINESATROOT); 

////添加几个节点
	HTREEITEM root = m_Tree->InsertItem(_T("root"),NULL,NULL);///root就是节点的标题 
int i,j; 
for (i=0;i<4;i++) { 
HTREEITEM childNode = m_Tree->InsertItem(_T("item"),NULL,NULL,root); 
for (j=0;j<3;j++) { 
HTREEITEM ccNode = m_Tree->InsertItem(_T("subitem"),NULL,NULL,childNode);
m_Tree->InsertItem(_T("subsubItem"),ccNode);
}
}




	// TODO: Add your control notification handler code here
	//OnOK();
}

void TestDlg::OnBnClickedButton1()
{

	////删除节点
CTreeCtrl* m_Tree = (CTreeCtrl*)GetDlgItem(IDC_TREE1);
	m_Tree->DeleteItem(m_Tree->GetSelectedItem());



	////MFC tree控件  获取选中

//	HTREEITEM selItem = m_Tree->GetSelectedItem();
//CString rlt = m_Tree->GetItemText(selItem);
//AfxMessageBox(rlt);
	// TODO: Add your control notification handler code here
}

void TestDlg::OnBnClickedButton2()
{
	//================================日历控件========================
	CMonthCalCtrl* ctlCalendar = (CMonthCalCtrl*)GetDlgItem(IDC_MONTHCALENDAR1);

	////获取当前选中的日期
	SYSTEMTIME* t = new SYSTEMTIME ();
	//ctlCalendar->GetCurSel(t);
    CString m_str;

	//m_str.Format(_T("%d-%d-%d  %d:%d:%d"),t->wYear,t->wMonth,t->wDay,
	//	t->wHour,t->wMinute,t->wSecond);
	//AfxMessageBox(m_str);
	// TODO: Add your control notification handler code here
    //================================日期控件============================

	CTime st ;
		CDateTimeCtrl* ctlDatetime = (CDateTimeCtrl*)GetDlgItem(IDC_DATETIMEPICKER1);
		ctlDatetime->GetTime(st);

m_str.Format(_T("%d-%d-%d  %d:%d:%d"),st.GetYear(),st.GetMonth(),st.GetDay(),
		st.GetHour(),st.GetMinute(),st.GetSecond());
AfxMessageBox(m_str);
free(t);
		
}

void TestDlg::OnBnClickedButton3()
{
	// TODO: Add your control notification handler code here
}

void TestDlg::OnHiMfc()
{
	// TODO: Add your command handler code here
}
