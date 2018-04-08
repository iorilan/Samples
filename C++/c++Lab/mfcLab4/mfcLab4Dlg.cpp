// mfcLab4Dlg.cpp : implementation file
//

#include "stdafx.h"
#include "mfcLab4.h"
#include "mfcLab4Dlg.h"
#include "TestDlg.h"
#include "FileDlg.h"
#include <string>
#include "DrawDlg.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


// CmfcLab4Dlg dialog




CmfcLab4Dlg::CmfcLab4Dlg(CWnd* pParent /*=NULL*/)
	: CDialog(CmfcLab4Dlg::IDD, pParent)
{
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
}

void CmfcLab4Dlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
}

BEGIN_MESSAGE_MAP(CmfcLab4Dlg, CDialog)
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	//}}AFX_MSG_MAP
	ON_BN_CLICKED(IDOK, &CmfcLab4Dlg::OnBnClickedOk)
	ON_BN_CLICKED(IDCANCEL, &CmfcLab4Dlg::OnBnClickedCancel)
	ON_BN_CLICKED(IDC_BtnLstSel, &CmfcLab4Dlg::OnBnClickedBtnlstsel)
	ON_BN_CLICKED(IDC_BTNNEWFRM, &CmfcLab4Dlg::OnBnClickedBtnnewfrm)
	ON_BN_CLICKED(IDC_BTNSHOWFILE, &CmfcLab4Dlg::OnBnClickedBtnshowfile)
	ON_BN_CLICKED(IDC_BTNDRAW, &CmfcLab4Dlg::OnBnClickedBtndraw)
//	ON_WM_MENUSELECT()
ON_BN_CLICKED(IDC_BTNMENU, &CmfcLab4Dlg::OnBnClickedBtnmenu)
ON_COMMAND(ID_HI_MFC, &CmfcLab4Dlg::OnHiMfc)
                       
 
ON_WM_INITMENUPOPUP()

END_MESSAGE_MAP()


// CmfcLab4Dlg message handlers

BOOL CmfcLab4Dlg::OnInitDialog()
{
	CDialog::OnInitDialog();

	// Set the icon for this dialog.  The framework does this automatically
	//  when the application's main window is not a dialog
	SetIcon(m_hIcon, TRUE);			// Set big icon
	SetIcon(m_hIcon, FALSE);		// Set small icon



			CMenu* menu = new CMenu();
 menu->LoadMenuW(IDR_MENU1);
 SetMenu(menu);
 
	// TODO: Add extra initialization here

	return TRUE;  // return TRUE  unless you set the focus to a control
}

// If you add a minimize button to your dialog, you will need the code below
//  to draw the icon.  For MFC applications using the document/view model,
//  this is automatically done for you by the framework.

void CmfcLab4Dlg::OnPaint()
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
HCURSOR CmfcLab4Dlg::OnQueryDragIcon()
{
	return static_cast<HCURSOR>(m_hIcon);
}




void CmfcLab4Dlg::OnBnClickedOk()
{

	// TODO: Add your control notification handler code here
	/*OnOK();*/
	  using namespace std;
	 
	//标签//////////////////////////////////////
	//CStatic* cw= (CStatic*)GetDlgItem(IDC_STATIC);
	////设置标签内容   
	//cw->SetWindowTextW(_T("aaa"));
	////获取标签内容
	//   CString s ;
	//   cw->GetWindowTextW(s);

	////输入框///////////////////////////////
	 //  CEdit* ce = (CEdit*)GetDlgItem(IDC_EDIT2);
	//   ce->SetWindowTextW(_T("bb"));
	//   ce->GetWindowTextW(s);

	////单选框////////////////////////////////
	//   CButton* radio1 = (CButton*)GetDlgItem(IDC_RADIO1);

	 //  CButton* radio2 = (CButton*)GetDlgItem(IDC_RADIO2);
	/*   if(radio1->GetCheck() !=NULL){
	   MessageBox(_T("radio1 checked"),0,0);
	   radio1->SetCheck(0);
	   radio2->SetCheck(1);
	   }
	   if(radio2->GetCheck() != NULL){
		   radio2->SetCheck(0);
		   radio1->SetCheck(1);
	   MessageBox(_T("radio2 checked"));
	   }*/


	////复选框/////////////////////////////////

//CButton* chkBtn1 = (CButton*)GetDlgItem(IDC_CHECK1);
//CButton* chkBtn2 = (CButton*)GetDlgItem(IDC_CHECK2);
// if(chkBtn1->GetCheck() !=NULL){
//	   MessageBox(_T("check1 checked"),0,0);
//	   chkBtn2->SetCheck(1);
//	   }
//  if(chkBtn2->GetCheck() !=NULL){
//	   MessageBox(_T("radio2 checked"),0,0);
//	   chkBtn1->SetCheck(0);
//	   chkBtn2->SetCheck(0);
//	   }

	/////测试数据源//////////////////////
  CStringList* dataSource = new CStringList();
dataSource->AddTail(_T("aaa"));
dataSource->AddTail(_T("bbb"));
dataSource->AddTail(_T("ccc"));
dataSource->AddTail(_T("ddd"));
dataSource->AddTail(_T("aaa"));
dataSource->AddTail(_T("aaa"));
dataSource->AddTail(_T("bbb"));
dataSource->AddTail(_T("ccc"));
dataSource->AddTail(_T("ddd"));
dataSource->AddTail(_T("aaa"));
dataSource->AddTail(_T("aaa"));
dataSource->AddTail(_T("bbb"));
dataSource->AddTail(_T("ccc"));
dataSource->AddTail(_T("ddd"));
dataSource->AddTail(_T("aaa"));

	////下拉框/////////////////////////////
//  CComboBox* cb = (CComboBox*)GetDlgItem(IDC_COMBO1);//获得当前combobox句柄
//
//  ////data source
//
//POSITION rPos;
//rPos = dataSource->GetHeadPosition();
//while (rPos != NULL)
//{
//   CString s= dataSource->GetNext(rPos);
//   ////去重
//   if(cb->FindString(0,s) < 0){
//   cb->AddString(s);
//   }
//}
//
//cb->SetCurSel(2);//设置当前选中项
//
//	////获取选中项
//CString cbSel;
//cb->GetLBText(cb->GetCurSel(),cbSel);
//MessageBox(cbSel,0,0);
//cb->ResetContent();//清空
  
	////列表框////////////////////////////
CListBox* cl = (CListBox*)GetDlgItem(IDC_LIST2);

POSITION rPos1;
rPos1 = dataSource->GetHeadPosition();
while (rPos1 != NULL)
{
   CString s= dataSource->GetNext(rPos1);
   ////去重
   if(cl->FindString(0,s) < 0){
   cl->AddString(s);
   }
}

////设置选中

//
//////获取选中项

if(cl->GetCurSel() >= 0){
CString strLstSel;
cl->GetText(cl->GetCurSel(),strLstSel);
MessageBox(strLstSel,0,0);
}
	//


}

void CmfcLab4Dlg::OnBnClickedCancel()
{
	// TODO: Add your control notification handler code here
	//OnCancel();
	////下拉框  移除所有
	CComboBox* cb = (CComboBox*)GetDlgItem(IDC_COMBO1);
	cb->ResetContent();
}


void CmfcLab4Dlg::OnBnClickedBtnlstsel()
{

try{
CFile f;
f.Open(_T("D:/111.txt"),CFile::modeReadWrite);
			////列表框，获取选中
CListBox* cl = (CListBox*)GetDlgItem(IDC_LIST2);
CString strLstSel;
if(cl->GetCurSel() < 0){
return;
}
cl->GetText(cl->GetCurSel(),strLstSel);
MessageBox(strLstSel,0,0);
}

catch(CException* e){
	CString str;
	AfxMessageBox(_T("error"));
}

	// TODO: Add your control notification handler code here
}

////弹出新对话框
void CmfcLab4Dlg::OnBnClickedBtnnewfrm()
{
	TestDlg* dlg = new TestDlg();
	dlg->DoModal();
	// TODO: Add your control notification handler code here
}

void CmfcLab4Dlg::OnBnClickedBtnshowfile()
{
FileDlg* dlg = new FileDlg();
	dlg->DoModal();
	// TODO: Add your control notification handler code here
}


void CmfcLab4Dlg::OnBnClickedBtndraw()
{
DrawDlg* dlg = new DrawDlg();
	dlg->DoModal();
	// TODO: Add your control notification handler code here
}


void CmfcLab4Dlg::OnBnClickedBtnmenu()
{
	 CMenu* menu = new CMenu();
	  menu->LoadMenuW(IDR_MENU1);
	  SetMenu(menu);
	// TODO: Add your control notification handler code here
}



bool m_bChecked = false;

void CmfcLab4Dlg::OnHiMfc()
{
	m_bChecked = !m_bChecked;
}



void CmfcLab4Dlg::OnInitMenuPopup(CMenu *pPopupMenu, UINT nIndex,BOOL bSysMenu)
{
   if (!bSysMenu)
    {
        //查看弹出菜单中是否包含 ID_SHUTDOWN 项。
        int nCount = pPopupMenu->GetMenuItemCount();
        for (int i = 0; i < nCount; i++)
        {
            if (pPopupMenu->GetMenuItemID(i) == ID_HI_MFC)
            {
                pPopupMenu->CheckMenuItem(ID_HI_MFC, MF_BYCOMMAND | (m_bChecked ? MF_CHECKED : MF_UNCHECKED));
                break;
            }
        }
    }
}
