// MainFrm.h : interface of the CMainFrame class
//


#pragma once

class CMainFrame : public CMDIFrameWnd
{
	
public:
	CMainFrame();
//DECLARE_DYNAMIC(CMainFrame)
// Attributes
public:
	
// Operations
public:
	virtual void OnInitMenuPopup(CMenu* pPop,UINT index,BOOL bSysMenu);
// Overrides
public:
	virtual BOOL PreCreateWindow(CREATESTRUCT& cs);
	
// Implementation
public:
	virtual ~CMainFrame();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:  // control bar embedded members
	CStatusBar  m_wndStatusBar;
	CToolBar    m_wndToolBar;

// Generated message map functions
protected:
	afx_msg int OnCreate(LPCREATESTRUCT lpCreateStruct);
	DECLARE_MESSAGE_MAP()
};


