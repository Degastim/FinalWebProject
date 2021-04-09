package com.epam.pharmacy.command;

import com.epam.pharmacy.command.impl.*;

/**
 * ActionCommand's implementations enumeration
 *
 * @author Yauheni Tsitou
 */
public enum CommandType {

    /**
     * Represents LoginCommand implementation of ActionCommand interface.
     */
    LOGIN {
        {
            this.command = new LoginCommand();
        }
    },

    /**
     * Represents LogoutCommand implementation of ActionCommand interface.
     */
    LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    },

    /**
     * Represents RegistrationCommand implementation of ActionCommand interface.
     */
    REGISTRATION {
        {
            this.command = new RegistrationCommand();
        }
    },

    /**
     * Represents RedirectToRegistrationCommand implementation of ActionCommand interface.
     */
    REDIRECT_TO_REGISTRATION {
        {
            this.command = new RedirectToRegistrationCommand();
        }
    },

    /**
     * Represents RedirectToLoginCommand implementation of ActionCommand interface.
     */
    REDIRECT_TO_LOGIN {
        {
            this.command = new RedirectToLoginCommand();
        }
    },

    /**
     * Represents ChangePasswordCommand implementation of ActionCommand interface.
     */
    CHANGE_PASSWORD {
        {
            this.command = new ChangePasswordCommand();
        }
    },

    /**
     * Represents PaginationCommand implementation of ActionCommand interface.
     */
    PAGINATION {
        {
            this.command = new PaginationCommand();
        }
    },

    /**
     * Represents RedirectToDrugTableCommand implementation of ActionCommand interface.
     */
    REDIRECT_TO_DRUG_TABLE {
        {
            this.command = new RedirectToDrugTableCommand();
        }
    },

    /**
     * Represents RedirectToMainCommand implementation of ActionCommand interface.
     */
    REDIRECT_TO_MAIN {
        {
            this.command = new RedirectToMainCommand();
        }
    },

    /**
     * Represents RedirectToEditDrugCommand implementation of ActionCommand interface.
     */
    REDIRECT_TO_EDIT_DRUG {
        {
            this.command = new RedirectToEditDrugCommand();
        }
    },

    /**
     * Represents RedirectToStartCommand implementation of ActionCommand interface.
     */
    REDIRECT_TO_START {
        {
            this.command = new RedirectToStartCommand();
        }
    },

    /**
     * Represents EditDrugCommand implementation of ActionCommand interface.
     */
    EDIT_DRUG {
        {
            this.command = new EditDrugCommand();
        }
    },

    /**
     * Represents RedirectToAddDrugCommand implementation of ActionCommand interface.
     */
    REDIRECT_TO_ADD_DRUG {
        {
            this.command = new RedirectToAddDrugCommand();
        }
    },

    /**
     * Represents AddDrugCommand implementation of ActionCommand interface.
     */
    ADD_DRUG {
        {
            this.command = new AddDrugCommand();
        }
    },

    /**
     * Represents ChangeLocalCommand implementation of ActionCommand interface.
     */
    CHANGE_LOCALE {
        {
            this.command = new ChangeLocalCommand();
        }
    },

    /**
     * Represents RedirectToChangePassword implementation of ActionCommand interface.
     */
    REDIRECT_TO_CHANGE_PASSWORD {
        {
            this.command = new RedirectToChangePassword();
        }
    },

    /**
     * Represents RedirectToCustomerPrescriptionTableCommand implementation of ActionCommand interface.
     */
    REDIRECT_TO_CUSTOMER_PRESCRIPTION_TABLE {
        {
            this.command = new RedirectToCustomerPrescriptionTableCommand();
        }
    },

    /**
     * Represents PrescriptionRenewalCommand implementation of ActionCommand interface.
     */
    PRESCRIPTION_RENEWAL {
        {
            this.command = new PrescriptionRenewalCommand();
        }
    },

    /**
     * Represents RedirectToAddPrescriptionOrder implementation of ActionCommand interface.
     */
    REDIRECT_TO_ADD_PRESCRIPTION_ORDER {
        {
            this.command = new RedirectToAddPrescriptionOrder();
        }
    },

    /**
     * Represents AddPrescriptionOrderCommand implementation of ActionCommand interface.
     */
    ADD_PRESCRIPTION_ORDER {
        {
            this.command = new AddPrescriptionOrderCommand();
        }
    },

    /**
     * Represents CodeConfirmationCommand implementation of ActionCommand interface.
     */
    CODE_CONFIRMATION {
        {
            this.command = new CodeConfirmationCommand();
        }
    },

    /**
     * Represents RedirectToCodeConfirmation implementation of ActionCommand interface.
     */
    REDIRECT_TO_CODE_CONFIRMATION {
        {
            this.command = new RedirectToCodeConfirmation();
        }
    },

    /**
     * Represents RedirectToPrescriptionOrderTable implementation of ActionCommand interface.
     */
    REDIRECT_TO_PRESCRIPTION_ORDER_TABLE {
        {
            this.command = new RedirectToPrescriptionOrderTable();
        }
    },

    /**
     * Represents RedirectToPrescriptionOrder implementation of ActionCommand interface.
     */
    REDIRECT_TO_PRESCRIPTION_ORDER {
        {
            this.command = new RedirectToPrescriptionOrder();
        }
    },

    /**
     * Represents PrescriptionOrderConfirmationCommand implementation of ActionCommand interface.
     */
    PRESCRIPTION_ORDER_CONFIRMATION {
        {
            this.command = new PrescriptionOrderConfirmationCommand();
        }
    },

    /**
     * Represents RefusalPrescriptionOrderCommand implementation of ActionCommand interface.
     */
    REFUSAL_PRESCRIPTION_ORDER {
        {
            this.command = new RefusalPrescriptionOrderCommand();
        }
    },

    /**
     * Represents RedirectToDrugOrderForm implementation of ActionCommand interface.
     */
    REDIRECT_TO_DRUG_ORDER_FORM {
        {
            this.command = new RedirectToDrugOrderForm();
        }
    },

    /**
     * Represents DrugOrderCommand implementation of ActionCommand interface.
     */
    DRUG_ORDER {
        {
            this.command = new DrugOrderCommand();
        }
    },

    /**
     * Represents DeleteDrugCommand implementation of ActionCommand interface.
     */
    DELETE_DRUG {
        {
            this.command = new DeleteDrugCommand();
        }
    },

    /**
     * Represents DeleteDrugPictureCommand implementation of ActionCommand interface.
     */
    DELETE_DRUG_PICTURE {
        {
            this.command = new DeleteDrugPictureCommand();
        }
    },

    /**
     * Represents RedirectToCustomerOrderTable implementation of ActionCommand interface.
     */
    REDIRECT_TO_CUSTOMER_ORDER_TABLE {
        {
            this.command = new RedirectToCustomerOrderTable();
        }
    },

    /**
     * Represents RedirectToPharmacistDrugOrderTable implementation of ActionCommand interface.
     */
    REDIRECT_TO_PHARMACIST_DRUG_ORDER_TABLE {
        {
            this.command = new RedirectToPharmacistDrugOrderTable();
        }
    },

    /**
     * Represents ConfirmDrugOrder implementation of ActionCommand interface.
     */
    CONFIRM_DRUG_ORDER {
        {
            this.command = new ConfirmDrugOrder();
        }
    },

    /**
     * Represents RefuseDrugOrder implementation of ActionCommand interface.
     */
    REFUSE_DRUG_ORDER {
        {
            this.command = new RefuseDrugOrder();
        }
    },

    /**
     * Represents RedirectToAccountReplenishment implementation of ActionCommand interface.
     */
    REDIRECT_TO_ACCOUNT_REPLENISHMENT {
        {
            this.command = new RedirectToAccountReplenishment();
        }
    },

    /**
     * Represents AccountReplenishment implementation of ActionCommand interface.
     */
    ACCOUNT_REPLENISHMENT {
        {
            this.command = new AccountReplenishment();
        }
    };
    /**
     * The value is used for character command.
     */
    ActionCommand command;

    /**
     * Getter method of ActionCommand's implementation.
     *
     * @return implementation of ActionCommand interface.
     */
    public ActionCommand getCurrentCommand() {
        return command;
    }
}
