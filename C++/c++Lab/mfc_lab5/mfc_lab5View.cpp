// mfc_lab5View.cpp : implementation of the Cmfc_lab5View class
//

#include "stdafx.h"
#include "mfc_lab5.h"

#include "mfc_lab5Doc.h"
#include "mfc_lab5View.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


// Cmfc_lab5View

IMPLEMENT_DYNCREATE(Cmfc_lab5View, CView)

BEGIN_MESSAGE_MAP(Cmfc_lab5View, CView)
	// Standard printing commands
	ON_COMMAND(ID_FILE_PRINT, &CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_DIRECT, &CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_PREVIEW, &CView::OnFilePrintPreview)
END_MESSAGE_MAP()

// Cmfc_lab5View construction/destruction

Cmfc_lab5View::Cmfc_lab5View()
{
	// TODO: add construction code here

}

Cmfc_lab5View::~Cmfc_lab5View()
{
}

BOOL Cmfc_lab5View::PreCreateWindow(CREATESTRUCT& cs)
{
	// TODO: Modify the Window class or styles here by modifying
	//  the CREATESTRUCT cs

	return CView::PreCreateWindow(cs);
}

// Cmfc_lab5View drawing

void Cmfc_lab5View::OnDraw(CDC* /*pDC*/)
{
	Cmfc_lab5Doc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);
	if (!pDoc)
		return;

	// TODO: add draw code for native data here
}


// Cmfc_lab5View printing

BOOL Cmfc_lab5View::OnPreparePrinting(CPrintInfo* pInfo)
{
	// default preparation
	return DoPreparePrinting(pInfo);
}

void Cmfc_lab5View::OnBeginPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: add extra initialization before printing
}

void Cmfc_lab5View::OnEndPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: add cleanup after printing
}


// Cmfc_lab5View diagnostics

#ifdef _DEBUG
void Cmfc_lab5View::AssertValid() const
{
	CView::AssertValid();
}

void Cmfc_lab5View::Dump(CDumpContext& dc) const
{
	CView::Dump(dc);
}

Cmfc_lab5Doc* Cmfc_lab5View::GetDocument() const // non-debug version is inline
{
	ASSERT(m_pDocument->IsKindOf(RUNTIME_CLASS(Cmfc_lab5Doc)));
	return (Cmfc_lab5Doc*)m_pDocument;
}
#endif //_DEBUG


// Cmfc_lab5View message handlers
