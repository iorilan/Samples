// MfcFileTestDlg.cpp : implementation file
//

#include "stdafx.h"
#include "MfcFileTest.h"
#include "MfcFileTestDlg.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


// CMfcFileTestDlg dialog




CMfcFileTestDlg::CMfcFileTestDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CMfcFileTestDlg::IDD, pParent)
{
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
}

void CMfcFileTestDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
}

BEGIN_MESSAGE_MAP(CMfcFileTestDlg, CDialog)
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	//}}AFX_MSG_MAP
	ON_BN_CLICKED(IDC_BTNSELFILE, &CMfcFileTestDlg::OnBnClickedBtnselfile)
END_MESSAGE_MAP()


// CMfcFileTestDlg message handlers

BOOL CMfcFileTestDlg::OnInitDialog()
{
	CDialog::OnInitDialog();

	// Set the icon for this dialog.  The framework does this automatically
	//  when the application's main window is not a dialog
	SetIcon(m_hIcon, TRUE);			// Set big icon
	SetIcon(m_hIcon, FALSE);		// Set small icon

	// TODO: Add extra initialization here


	////获取表格控件指针、设置样式
	CListCtrl* lst = (CListCtrl*)GetDlgItem(IDC_LSTCTl);
	lst->ModifyStyle( 0, LVS_REPORT );               // 报表模式  
	lst->SetExtendedStyle(lst->GetExtendedStyle() 
					 | LVS_EX_GRIDLINES 
					 | LVS_EX_FULLROWSELECT);  

	lst->InsertColumn(0,_T("第一列"));  
	lst->InsertColumn(1,_T("第二列"));  

//////设置列

	CRect rect;
lst->GetClientRect(rect); //获得当前客户区信息
lst->SetColumnWidth(0, rect.Width() / 2); //设置列的宽度。  
lst->SetColumnWidth(1, rect.Width() / 2);  


/////插入几行

lst->InsertItem(0, _T("1.1"));  
lst->SetItemText(0, 1, _T("2.1"));  

lst->InsertItem(1, _T("1.2"));  
lst->SetItemText(1, 1, _T("2.2"));  

lst->InsertItem(2, _T("1.3"));  
lst->SetItemText(2, 1, _T("2.3"));  

/////删除

lst->DeleteItem(1);

////遍历，取第一列的数据

CString str("");
int lineCount = lst->GetItemCount();
int i = 0;
for(i = 0;i < lineCount;i ++){
	str.Append(lst->GetItemText(i,0) + _T(" , "));
}
AfxMessageBox(str);

	return TRUE;  // return TRUE  unless you set the focus to a control
}

// If you add a minimize button to your dialog, you will need the code below
//  to draw the icon.  For MFC applications using the document/view model,
//  this is automatically done for you by the framework.

void CMfcFileTestDlg::OnPaint()
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
HCURSOR CMfcFileTestDlg::OnQueryDragIcon()
{
	return static_cast<HCURSOR>(m_hIcon);
}

CStringList fileLines;

void CMfcFileTestDlg::OnBnClickedBtnselfile()
{

CFileDialog dlg(TRUE, NULL, NULL,
           OFN_HIDEREADONLY | OFN_OVERWRITEPROMPT,
           NULL);

    if(dlg.DoModal() == IDOK)
    {



 /*   CString szFilename = dlg.GetPathName();
	CStdioFile mFile;
	mFile.Open(szFilename,CFile::modeRead);

    CString tmp("");
	while(mFile.ReadString(tmp)){
	  fileLines.AddTail(tmp);
	  }
	mFile.Close();*/
	}
	// TODO: Add your control notification handler code here
}

