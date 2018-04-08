// mfc_lab7Dlg.cpp : implementation file
//

#include "stdafx.h"
#include "mfc_lab7.h"
#include "mfc_lab7Dlg.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


// Cmfc_lab7Dlg dialog




Cmfc_lab7Dlg::Cmfc_lab7Dlg(CWnd* pParent /*=NULL*/)
	: CDialog(Cmfc_lab7Dlg::IDD, pParent)
{
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);

}

void Cmfc_lab7Dlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
}

BEGIN_MESSAGE_MAP(Cmfc_lab7Dlg, CDialog)
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	ON_WM_INITMENUPOPUP()//用于对话框菜单打勾 
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()


// Cmfc_lab7Dlg message handlers

BOOL Cmfc_lab7Dlg::OnInitDialog()
{
	CDialog::OnInitDialog();

			CMenu* menu =new CMenu;

menu->LoadMenu(MAKEINTRESOURCE(IDR_MENU1));      //红色部分为对应自己添加的菜单资源的名称

this->SetMenu(menu);


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

void Cmfc_lab7Dlg::OnPaint()
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
HCURSOR Cmfc_lab7Dlg::OnQueryDragIcon()
{
	return static_cast<HCURSOR>(m_hIcon);
}

void Cmfc_lab7Dlg::OnMenuSelect(UINT ID,UINT flag,UINT sysMenu){
CMenu* m = (CMenu*)GetDlgItem(IDR_MENU1);
m->CheckMenuItem(ID,flag);
}

void Cmfc_lab7Dlg::OnInitMenuPopup(CMenu *pPopupMenu, UINT nIndex, BOOL bSysMenu)
{
	 ASSERT(pPopupMenu   !=   NULL);   
          //   Check   the   enabled   state   of   various   menu   items.   
    
          CCmdUI   state;   
          state.m_pMenu   =   pPopupMenu;   
          ASSERT(state.m_pOther   ==   NULL);   
          ASSERT(state.m_pParentMenu   ==   NULL);   
    
          //   Determine   if   menu   is   popup   in   top-level   menu   and   set   m_pOther   to   
          //   it   if   so   (m_pParentMenu   ==   NULL   indicates   that   it   is   secondary   popup).   
          HMENU   hParentMenu;   
          if   (AfxGetThreadState()->m_hTrackingMenu   ==   pPopupMenu->m_hMenu)   
                  state.m_pParentMenu   =   pPopupMenu;         //   Parent   ==   child   for   tracking   popup.   
          else   if   ((hParentMenu   =   ::GetMenu(m_hWnd))   !=   NULL)   
          {   
                  CWnd*   pParent   =   this;   
                        //   Child   windows   don't   have   menus--need   to   go   to   the   top!   
                  if   (pParent   !=   NULL   &&   
                        (hParentMenu   =   ::GetMenu(pParent->m_hWnd))   !=   NULL)   
                  {   
                        int   nIndexMax   =   ::GetMenuItemCount(hParentMenu);   
                        for   (int   nIndex   =   0;   nIndex   <   nIndexMax;   nIndex++)   
                        {   
                          if   (::GetSubMenu(hParentMenu,   nIndex)   ==   pPopupMenu->m_hMenu)   
                          {   
                                  //   When   popup   is   found,   m_pParentMenu   is   containing   menu.   
                                  state.m_pParentMenu   =   CMenu::FromHandle(hParentMenu);   
                                  break;   
                          }   
                        }   
                  }   
          }   
    
          state.m_nIndexMax   =   pPopupMenu->GetMenuItemCount();   
          for   (state.m_nIndex   =   0;   state.m_nIndex   <   state.m_nIndexMax;   
              state.m_nIndex++)   
          {   
                  state.m_nID   =   pPopupMenu->GetMenuItemID(state.m_nIndex);   
                  if   (state.m_nID   ==   0)   
                        continue;   //   Menu   separator   or   invalid   cmd   -   ignore   it.   
    
                  ASSERT(state.m_pOther   ==   NULL);   
                  ASSERT(state.m_pMenu   !=   NULL);   
                  if   (state.m_nID   ==   (UINT)-1)   
                  {   
                        //   Possibly   a   popup   menu,   route   to   first   item   of   that   popup.   
                        state.m_pSubMenu   =   pPopupMenu->GetSubMenu(state.m_nIndex);   
                        if   (state.m_pSubMenu   ==   NULL   ||   
                          (state.m_nID   =   state.m_pSubMenu->GetMenuItemID(0))   ==   0   ||   
                          state.m_nID   ==   (UINT)-1)   
                        {   
                          continue;               //   First   item   of   popup   can't   be   routed   to.   
                        }   
                        state.DoUpdate(this,   TRUE);       //   Popups   are   never   auto   disabled.   
                  }   
                  else   
                  {   
                        //   Normal   menu   item.   
                        //   Auto   enable/disable   if   frame   window   has   m_bAutoMenuEnable   
                        //   set   and   command   is   _not_   a   system   command.   
                        state.m_pSubMenu   =   NULL;   
                        state.DoUpdate(this,   FALSE);   
                  }   
    
                  //   Adjust   for   menu   deletions   and   additions.   
                  UINT   nCount   =   pPopupMenu->GetMenuItemCount();   
                  if   (nCount   <   state.m_nIndexMax)   
                  {   
                        state.m_nIndex   -=   (state.m_nIndexMax   -   nCount);   
                        while   (state.m_nIndex   <   nCount   &&   
                          pPopupMenu->GetMenuItemID(state.m_nIndex)   ==   state.m_nID)   
                        {   
                          state.m_nIndex++;   
                        }   
                  }   
                  state.m_nIndexMax   =   nCount;   
          }   

}

