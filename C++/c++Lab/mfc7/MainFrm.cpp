// MainFrm.cpp : implementation of the CMainFrame class
//

#include "stdafx.h"
#include "mfc7.h"

#include "MainFrm.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


// CMainFrame

IMPLEMENT_DYNCREATE(CMainFrame, CFrameWnd)

BEGIN_MESSAGE_MAP(CMainFrame, CFrameWnd)
END_MESSAGE_MAP()


// CMainFrame construction/destruction

CMainFrame::CMainFrame()
{
	// TODO: add member initialization code here
}

CMainFrame::~CMainFrame()
{
}

BOOL CMainFrame::Create(LPCTSTR lpszClassName, LPCTSTR lpszWindowName, DWORD dwStyle , const RECT& rect , CWnd* pParentWnd , LPCTSTR lpszMenuName , DWORD dwExStyle , CCreateContext* pContext)  
{  
    // TODO: 在此添加专用代码和/或调用基类  
//  return CFrameWnd::Create(lpszClassName, lpszWindowName, dwStyle, rect, // //pParentWnd, lpszMenuName, dwExStyle, pContext);  
     HMENU nIDorHMenu = NULL;  
     return CFrameWnd::CreateEx(dwExStyle,lpszClassName,lpszWindowName,dwStyle,  
         rect.left,rect.top,rect.right-rect.left,rect.bottom-rect.top,  
         pParentWnd->GetSafeHwnd(),  
         nIDorHMenu,  
         (LPVOID)pContext);  
}

BOOL CMainFrame::PreCreateWindow(CREATESTRUCT& cs)
{
	if( !CFrameWnd::PreCreateWindow(cs) )
		return FALSE;
	// TODO: Modify the Window class or styles here by modifying
	//  the CREATESTRUCT cs

	cs.style = WS_OVERLAPPED | WS_CAPTION | FWS_ADDTOTITLE
		 | WS_THICKFRAME | WS_MINIMIZEBOX | WS_MAXIMIZEBOX;

	return TRUE;
}


// CMainFrame diagnostics

#ifdef _DEBUG
void CMainFrame::AssertValid() const
{
	CFrameWnd::AssertValid();
}

void CMainFrame::Dump(CDumpContext& dc) const
{
	CFrameWnd::Dump(dc);
}

#endif //_DEBUG


// CMainFrame message handlers



