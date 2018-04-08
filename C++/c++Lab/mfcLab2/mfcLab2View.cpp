// mfcLab2View.cpp : implementation of the CmfcLab2View class
//

#include "stdafx.h"
#include "mfcLab2.h"

#include "mfcLab2Doc.h"
#include "mfcLab2View.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


// CmfcLab2View

IMPLEMENT_DYNCREATE(CmfcLab2View, CView)

BEGIN_MESSAGE_MAP(CmfcLab2View, CView)
	// Standard printing commands
	ON_COMMAND(ID_FILE_PRINT, &CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_DIRECT, &CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_PREVIEW, &CView::OnFilePrintPreview)
END_MESSAGE_MAP()

// CmfcLab2View construction/destruction

CmfcLab2View::CmfcLab2View()
{
	// TODO: add construction code here

}

CmfcLab2View::~CmfcLab2View()
{
}

BOOL CmfcLab2View::PreCreateWindow(CREATESTRUCT& cs)
{
	// TODO: Modify the Window class or styles here by modifying
	//  the CREATESTRUCT cs

	return CView::PreCreateWindow(cs);
}

// CmfcLab2View drawing

void CmfcLab2View::OnDraw(CDC* /*pDC*/)
{
	CmfcLab2Doc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);
	if (!pDoc)
		return;

	// TODO: add draw code for native data here
}


// CmfcLab2View printing

BOOL CmfcLab2View::OnPreparePrinting(CPrintInfo* pInfo)
{
	// default preparation
	return DoPreparePrinting(pInfo);
}

void CmfcLab2View::OnBeginPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: add extra initialization before printing
}

void CmfcLab2View::OnEndPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: add cleanup after printing
}


// CmfcLab2View diagnostics

#ifdef _DEBUG
void CmfcLab2View::AssertValid() const
{
	CView::AssertValid();
}

void CmfcLab2View::Dump(CDumpContext& dc) const
{
	CView::Dump(dc);
}

CmfcLab2Doc* CmfcLab2View::GetDocument() const // non-debug version is inline
{
	ASSERT(m_pDocument->IsKindOf(RUNTIME_CLASS(CmfcLab2Doc)));
	return (CmfcLab2Doc*)m_pDocument;
}
#endif //_DEBUG


// CmfcLab2View message handlers
