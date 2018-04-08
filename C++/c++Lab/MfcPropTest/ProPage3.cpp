
#include "stdafx.h"
#include "MfcPropTest.h"
#include "ProPage3.h"


// ProPage3 dialog

IMPLEMENT_DYNAMIC(ProPage3, CPropertyPage)

ProPage3::ProPage3()
	: CPropertyPage(ProPage3::IDD)
{

}

ProPage3::~ProPage3()
{
}

BOOL ProPage3::OnSetActive(){
((CPropertySheet*)this->GetParent())->SetWizardButtons(PSWIZB_BACK);
return true;
}

void ProPage3::DoDataExchange(CDataExchange* pDX)
{
	CPropertyPage::DoDataExchange(pDX);
}


BEGIN_MESSAGE_MAP(ProPage3, CPropertyPage)
END_MESSAGE_MAP()


// ProPage3 message handlers
