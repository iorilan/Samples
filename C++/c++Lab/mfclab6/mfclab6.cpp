// mfclab6.cpp : Defines the class behaviors for the application.
//

#include "stdafx.h"
#include "mfclab6.h"
#include "MainFrm.h"

#include "ChildFrm.h"
#include "mfclab6Doc.h"
#include "mfclab6View.h"
#include "CPerson.h"
#ifdef _DEBUG
#define new DEBUG_NEW
#endif


// Cmfclab6App

BEGIN_MESSAGE_MAP(Cmfclab6App, CWinApp)
	ON_COMMAND(ID_APP_ABOUT, &Cmfclab6App::OnAppAbout)
	// Standard file based document commands
	ON_COMMAND(ID_FILE_NEW, &CWinApp::OnFileNew)
	ON_COMMAND(ID_FILE_OPEN, &CWinApp::OnFileOpen)
	// Standard print setup command
	ON_COMMAND(ID_FILE_PRINT_SETUP, &CWinApp::OnFilePrintSetup)
END_MESSAGE_MAP()


// Cmfclab6App construction

Cmfclab6App::Cmfclab6App()
{
	// TODO: add construction code here,
	// Place all significant initialization in InitInstance
}


// The one and only Cmfclab6App object

Cmfclab6App theApp;


// Cmfclab6App initialization

BOOL Cmfclab6App::InitInstance()
{
	// InitCommonControlsEx() is required on Windows XP if an application
	// manifest specifies use of ComCtl32.dll version 6 or later to enable
	// visual styles.  Otherwise, any window creation will fail.
	INITCOMMONCONTROLSEX InitCtrls;
	InitCtrls.dwSize = sizeof(InitCtrls);
	// Set this to include all the common control classes you want to use
	// in your application.
	InitCtrls.dwICC = ICC_WIN95_CLASSES;
	InitCommonControlsEx(&InitCtrls);

	CWinApp::InitInstance();

	// Initialize OLE libraries
	if (!AfxOleInit())
	{
		AfxMessageBox(IDP_OLE_INIT_FAILED);
		return FALSE;
	}
	AfxEnableControlContainer();
	// Standard initialization
	// If you are not using these features and wish to reduce the size
	// of your final executable, you should remove from the following
	// the specific initialization routines you do not need
	// Change the registry key under which our settings are stored
	// TODO: You should modify this string to be something appropriate
	// such as the name of your company or organization
	SetRegistryKey(_T("Local AppWizard-Generated Applications"));
	LoadStdProfileSettings(4);  // Load standard INI file options (including MRU)
	// Register the application's document templates.  Document templates
	//  serve as the connection between documents, frame windows and views
	CMultiDocTemplate* pDocTemplate;
	pDocTemplate = new CMultiDocTemplate(IDR_mfclab6TYPE,
		RUNTIME_CLASS(Cmfclab6Doc),
		RUNTIME_CLASS(CChildFrame), // custom MDI child frame
		RUNTIME_CLASS(Cmfclab6View));
	if (!pDocTemplate)
		return FALSE;
	AddDocTemplate(pDocTemplate);

	// create main MDI Frame window
	CMainFrame* pMainFrame = new CMainFrame;
	if (!pMainFrame || !pMainFrame->LoadFrame(IDR_MAINFRAME))
	{
		delete pMainFrame;
		return FALSE;
	}
	m_pMainWnd = pMainFrame;
	// call DragAcceptFiles only if there's a suffix
	//  In an MDI app, this should occur immediately after setting m_pMainWnd


	// Parse command line for standard shell commands, DDE, file open
	CCommandLineInfo cmdInfo;
	ParseCommandLine(cmdInfo);


	// Dispatch commands specified on the command line.  Will return FALSE if
	// app was launched with /RegServer, /Register, /Unregserver or /Unregister.
	if (!ProcessShellCommand(cmdInfo))
		return FALSE;
	// The main window has been initialized, so show and update it
	pMainFrame->ShowWindow(m_nCmdShow);
	pMainFrame->UpdateWindow();


	return TRUE;
}



// CAboutDlg dialog used for App About

class CAboutDlg : public CDialog
{
public:
	CAboutDlg();

// Dialog Data
	enum { IDD = IDD_ABOUTBOX };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support

// Implementation
protected:
	DECLARE_MESSAGE_MAP()
public:
	afx_msg void OnBnClickedOk();
	afx_msg void OnBnClickedButton1();
	afx_msg void OnBnClickedBtnmenu();
};

CAboutDlg::CAboutDlg() : CDialog(CAboutDlg::IDD)
{
}

void CAboutDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
}

BEGIN_MESSAGE_MAP(CAboutDlg, CDialog)
	ON_BN_CLICKED(IDOK, &CAboutDlg::OnBnClickedOk)
	ON_BN_CLICKED(IDC_BUTTON1, &CAboutDlg::OnBnClickedButton1)
	ON_BN_CLICKED(IDC_BTNMENU, &CAboutDlg::OnBnClickedBtnmenu)
END_MESSAGE_MAP()

// App command to run the dialog
void Cmfclab6App::OnAppAbout()
{

	CAboutDlg aboutDlg;
	aboutDlg.DoModal();
}


// Cmfclab6App message handlers


void CAboutDlg::OnBnClickedOk()
{
	CFile file(_T("D:/persons.archive"), CFile::modeCreate | CFile::modeWrite);  
    CArchive ar(&file, CArchive::store);  
		CPerson p(_T("aaa"),1,true);
		// TODO: add storing code here
		ar<<&p;
		 ar.Close();  
    file.Close();  
	
	
		// TODO: add loading code here
	
	// TODO: Add your control notification handler code here
	//OnOK();
}

void CAboutDlg::OnBnClickedButton1()
{
	CFile Rfile(_T("D:/persons.archive"), CFile::modeRead);  
    CArchive Rar(&Rfile, CArchive::load);  
    CPerson* p1;
    //序列化出来  
    Rar >> p1 ;  

	AfxMessageBox(p1->m_name);
	// TODO: Add your control notification handler code here
}

void CAboutDlg::OnBnClickedBtnmenu()
{
	
	// TODO: Add your control notification handler code here
}
