// mfclab6View.cpp : implementation of the Cmfclab6View class
//

#include "stdafx.h"
#include "mfclab6.h"

#include "mfclab6Doc.h"
#include "mfclab6View.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


// Cmfclab6View

IMPLEMENT_DYNCREATE(Cmfclab6View, CView)

BEGIN_MESSAGE_MAP(Cmfclab6View, CView)
	// Standard printing commands
	ON_COMMAND(ID_FILE_PRINT, &CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_DIRECT, &CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_PREVIEW, &CView::OnFilePrintPreview)
	//
	ON_WM_LBUTTONDOWN(UINT flags,CPoint p)
	ON_WM_MOUSEMOVE(UINT flags,CPoint p)
	ON_WM_LBUTTONUP(UINT flags,CPoint p)

	ON_COMMAND(ID_VIEW_HI, &Cmfclab6View::OnViewHi)
	ON_UPDATE_COMMAND_UI(ID_VIEW_HI, &Cmfclab6View::OnUpdateViewHi)
	ON_COMMAND(ID_A_C, &Cmfclab6View::OnAC)
	ON_UPDATE_COMMAND_UI(ID_A_B, &Cmfclab6View::OnUpdateAB)
	ON_COMMAND(ID_A_D, &Cmfclab6View::OnAD)
END_MESSAGE_MAP()

// Cmfclab6View construction/destruction

Cmfclab6View::Cmfclab6View()
{
	// TODO: add construction code here

this->isDrawing = false;
this->pen = new CPen(PS_SOLID,1,1);
}

Cmfclab6View::~Cmfclab6View()
{
}

BOOL Cmfclab6View::PreCreateWindow(CREATESTRUCT& cs)
{
	// TODO: Modify the Window class or styles here by modifying
	//  the CREATESTRUCT cs

	return CView::PreCreateWindow(cs);
}

// Cmfclab6View drawing

void Cmfclab6View::OnDraw(CDC* pDC)
{

	
	

	// TODO: add draw code for native data here
}


// Cmfclab6View printing

BOOL Cmfclab6View::OnPreparePrinting(CPrintInfo* pInfo)
{
	// default preparation
	return DoPreparePrinting(pInfo);
}

void Cmfclab6View::OnBeginPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: add extra initialization before printing
}

void Cmfclab6View::OnEndPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: add cleanup after printing
}

//////////////////////

void Cmfclab6View::OnLButtonDown(UINT F,CPoint p){
	this->prevPoint = p;
	SetCapture();
	this->isDrawing = true;
}

void Cmfclab6View::OnLButtonUp(UINT F,CPoint p){  
	
	if(GetCapture()!=this){
		return;
	}
	CClientDC dc(this);
	CPen* poldPen = dc.SelectObject(this->pen);
	dc.MoveTo(this->prevPoint.x,this->prevPoint.y);
	dc.LineTo(p.x,p.y);
	this->prevPoint = p;
	this->isDrawing = false;
	ReleaseCapture();
}

void Cmfclab6View::OnMouseMove(UINT F,CPoint p){
	if(this->isDrawing){
	CClientDC dc(this);
	CPen* poldPen = dc.SelectObject(this->pen);
	dc.MoveTo(this->prevPoint.x,this->prevPoint.y);
	dc.LineTo(p.x,p.y);
	this->prevPoint = p;
	}
}

///////////////////

// Cmfclab6View diagnostics

#ifdef _DEBUG
void Cmfclab6View::AssertValid() const
{
	CView::AssertValid();
}

void Cmfclab6View::Dump(CDumpContext& dc) const
{
	CView::Dump(dc);
}

Cmfclab6Doc* Cmfclab6View::GetDocument() const // non-debug version is inline
{
	ASSERT(m_pDocument->IsKindOf(RUNTIME_CLASS(Cmfclab6Doc)));
	return (Cmfclab6Doc*)m_pDocument;
}
#endif //_DEBUG


// Cmfclab6View message handlers

void Cmfclab6View::OnViewHi()
{
	//CheckMenuItem(menu,ID_VIEW_HI,MF_BYPOSITION | MF_CHECKED);
	// TODO: Add your command handler code here
}

void Cmfclab6View::OnUpdateViewHi(CCmdUI *pCmdUI)
{
	/*if(pCmdUI->m_pMenu->GetMenuItemCount() > 0){
	return;
	}	
	if(isCheck){
		pCmdUI->m_pSubMenu->SetCheck(1);
	isCheck = false;
	}else{
		pCmdUI->SetCheck(0);
	isCheck = true;
	}*/
	// TODO: Add your command update UI handler code here
}

void Cmfclab6View::OnAC()
{
CMenu menu ;
	menu.LoadMenu(IDR_MENU1);
	CMenu* subMenu = menu.GetSubMenu(0);
	subMenu->CheckMenuItem(0,MF_BYPOSITION | MF_CHECKED);
	// TODO: Add your command handler code here
}

void Cmfclab6View::OnUpdateAB(CCmdUI *pCmdUI)
{
	// TODO: Add your command update UI handler code here
}

void Cmfclab6View::OnAD()
{
	// TODO: Add your command handler code here
}
