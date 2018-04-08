// mfc7View.cpp : implementation of the Cmfc7View class
//

#include "stdafx.h"
#include "mfc7.h"

#include "mfc7Doc.h"
#include "mfc7View.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


// Cmfc7View

IMPLEMENT_DYNCREATE(Cmfc7View, CView)

BEGIN_MESSAGE_MAP(Cmfc7View, CView)
	// Standard printing commands
	ON_COMMAND(ID_FILE_PRINT, &CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_DIRECT, &CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_PREVIEW, &CView::OnFilePrintPreview)
END_MESSAGE_MAP()

// Cmfc7View construction/destruction

Cmfc7View::Cmfc7View()
{
	// TODO: add construction code here

}

Cmfc7View::~Cmfc7View()
{
}

BOOL Cmfc7View::PreCreateWindow(CREATESTRUCT& cs)
{
	// TODO: Modify the Window class or styles here by modifying
	//  the CREATESTRUCT cs

	return CView::PreCreateWindow(cs);
}

// Cmfc7View drawing

void Cmfc7View::OnDraw(CDC* /*pDC*/)
{
	Cmfc7Doc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);
	if (!pDoc)
		return;

	// TODO: add draw code for native data here
}


// Cmfc7View printing

BOOL Cmfc7View::OnPreparePrinting(CPrintInfo* pInfo)
{
	// default preparation
	return DoPreparePrinting(pInfo);
}

void Cmfc7View::OnBeginPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: add extra initialization before printing
}

void Cmfc7View::OnEndPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: add cleanup after printing
}


// Cmfc7View diagnostics

#ifdef _DEBUG
void Cmfc7View::AssertValid() const
{
	CView::AssertValid();
}

void Cmfc7View::Dump(CDumpContext& dc) const
{
	CView::Dump(dc);
}

Cmfc7Doc* Cmfc7View::GetDocument() const // non-debug version is inline
{
	ASSERT(m_pDocument->IsKindOf(RUNTIME_CLASS(Cmfc7Doc)));
	return (Cmfc7Doc*)m_pDocument;
}
#endif //_DEBUG


// Cmfc7View message handlers
