// mfcLab1View.cpp : implementation of the CmfcLab1View class
//

#include "stdafx.h"
#include "mfcLab1.h"

#include "mfcLab1Doc.h"
#include "mfcLab1View.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


// CmfcLab1View

IMPLEMENT_DYNCREATE(CmfcLab1View, CView)

BEGIN_MESSAGE_MAP(CmfcLab1View, CView)
	// Standard printing commands
	ON_COMMAND(ID_FILE_PRINT, &CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_DIRECT, &CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_PREVIEW, &CView::OnFilePrintPreview)
END_MESSAGE_MAP()

// CmfcLab1View construction/destruction

CmfcLab1View::CmfcLab1View()
{
	// TODO: add construction code here

}

CmfcLab1View::~CmfcLab1View()
{
}

BOOL CmfcLab1View::PreCreateWindow(CREATESTRUCT& cs)
{
	// TODO: Modify the Window class or styles here by modifying
	//  the CREATESTRUCT cs

	return CView::PreCreateWindow(cs);
}

// CmfcLab1View drawing

void CmfcLab1View::OnDraw(CDC* /*pDC*/)
{
	CmfcLab1Doc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);
	if (!pDoc)
		return;

	// TODO: add draw code for native data here
}


// CmfcLab1View printing

BOOL CmfcLab1View::OnPreparePrinting(CPrintInfo* pInfo)
{
	// default preparation
	return DoPreparePrinting(pInfo);
}

void CmfcLab1View::OnBeginPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: add extra initialization before printing
}

void CmfcLab1View::OnEndPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: add cleanup after printing
}


// CmfcLab1View diagnostics

#ifdef _DEBUG
void CmfcLab1View::AssertValid() const
{
	CView::AssertValid();
}

void CmfcLab1View::Dump(CDumpContext& dc) const
{
	CView::Dump(dc);
}

CmfcLab1Doc* CmfcLab1View::GetDocument() const // non-debug version is inline
{
	ASSERT(m_pDocument->IsKindOf(RUNTIME_CLASS(CmfcLab1Doc)));
	return (CmfcLab1Doc*)m_pDocument;
}
#endif //_DEBUG


// CmfcLab1View message handlers
